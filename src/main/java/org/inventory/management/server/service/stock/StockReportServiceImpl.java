package org.inventory.management.server.service.stock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.*;
import org.inventory.management.server.model.statistic.ChartData;
import org.inventory.management.server.model.stock.StockReportModelRes;
import org.inventory.management.server.model.stock.StockReportRequest;
import org.inventory.management.server.model.stockDetail.StockReportDetailModelRes;
import org.inventory.management.server.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockReportServiceImpl implements StockReportService {
    private final StockReportRepository stockReportRepository;
    private final ProductRepository productRepository;
    private final StockReportDetailRepository stockReportDetailRepository;
    private final InboundReportDetailRepository inboundReportDetailRepository;
    private final ModelMapper mapper;
    private final OutboundReportDetailRepository outboundReportDetailRepository;

    public StockReportDetail createStockReportDetail(Product product, StockReport stockReport) {
        // Fetch inbound report details and calculate total price and quantity
        List<InboundReportDetail> details = inboundReportDetailRepository.findByProductAndCreatedDateBetween(
                product, stockReport.getStartDate(), stockReport.getEndDate());
        AtomicReference<BigDecimal> inboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger inboundQuantity = new AtomicInteger();
        details.forEach(detail -> {
            inboundPrice.updateAndGet(v -> v.add(detail.getTotalPrice()));
            inboundQuantity.addAndGet(detail.getQuantity());
        });

        // Fetch outbound report details and calculate total price and quantity
        List<OutboundReportDetail> outboundDetails = outboundReportDetailRepository.findByProductAndCreatedDateBetween(
                product, stockReport.getStartDate(), stockReport.getEndDate());
        AtomicReference<BigDecimal> outboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger outboundQuantity = new AtomicInteger();
        outboundDetails.forEach(detail -> {
            outboundPrice.updateAndGet(v -> v.add(detail.getTotalPrice()));
            outboundQuantity.addAndGet(detail.getQuantity());
        });

        // Calculate needInboundQuantity, ensuring it is >= 0
        int calculatedNeedInboundQuantity = product.getMinQuantity() - product.getQuantity();
        int needInboundQuantity = Math.max(calculatedNeedInboundQuantity, 0);

        // Build and return StockReportDetail
        return StockReportDetail.builder()
                .product(product)
                .stockQuantity(product.getMaxQuantity())
                .inboundQuantity(inboundQuantity.get())
                .outboundQuantity(outboundQuantity.get())
                .inboundPrice(inboundPrice.get())
                .outboundPrice(outboundPrice.get())
                .needInboundQuantity(needInboundQuantity)
                .build();
    }

    @Transactional
    @Override
    public StockReportModelRes createStockReport(StockReportRequest request) {
        StockReport stockReport = mapper.map(request, StockReport.class);
        stockReport.setItems(new ArrayList<>());
        StockReport stockReportData = stockReportRepository.save(stockReport);
        List<Product> products = productRepository.findAll();
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger quantity = new AtomicInteger();
        AtomicInteger neededInboundQuantity = new AtomicInteger();
        AtomicReference<BigDecimal> inboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger inboundQuantity = new AtomicInteger();
        AtomicReference<BigDecimal> outboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger outboundQuantity = new AtomicInteger();

        List<StockReportDetail> items = products.stream()
                .map(item -> createStockReportDetail(item, stockReportData))
                .peek(detail -> {
                    outboundPrice.updateAndGet(v -> v.add(detail.getOutboundPrice()));
                    outboundQuantity.set(outboundQuantity.get() + detail.getOutboundQuantity());
                    inboundPrice.updateAndGet(v -> v.add(detail.getInboundPrice()));
                    inboundQuantity.set(inboundQuantity.get() + detail.getInboundQuantity());
                    neededInboundQuantity.set(neededInboundQuantity.get() + detail.getNeedInboundQuantity());
                    quantity.set(inboundQuantity.get() + detail.getStockQuantity());
                })
                .collect(Collectors.toList());
        stockReportData.setItems(items);
        stockReportData.setStockQuantity(quantity.get());
        stockReportData.setInboundPrice(inboundPrice.get());
        stockReportData.setOutboundPrice(outboundPrice.get());
        stockReportData.setOutboundQuantity(outboundQuantity.get());
        stockReportData.setInboundQuantity(inboundQuantity.get());
        stockReportData.setTotalPrice(outboundPrice.get().subtract(inboundPrice.get()));
        stockReportData.setNeedInboundQuantity(neededInboundQuantity.get());
        stockReportRepository.save(stockReportData);
        return mapper.map(stockReportData, StockReportModelRes.class);
    }

    @Override
    public StockReportModelRes getStockReport(StockReportRequest request) {
        StockReport stockReportData = mapper.map(request, StockReport.class);
        stockReportData.setItems(new ArrayList<>());
        List<Product> products = productRepository.findAll();
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger quantity = new AtomicInteger();
        AtomicInteger neededInboundQuantity = new AtomicInteger();
        AtomicReference<BigDecimal> inboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger inboundQuantity = new AtomicInteger();
        AtomicReference<BigDecimal> outboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger outboundQuantity = new AtomicInteger();

        List<StockReportDetail> items = products.stream()
                .map(item -> createStockReportDetail(item, stockReportData))
                .peek(detail -> {
                    outboundPrice.updateAndGet(v -> v.add(detail.getOutboundPrice()));
                    outboundQuantity.set(outboundQuantity.get() + detail.getOutboundQuantity());
                    inboundPrice.updateAndGet(v -> v.add(detail.getInboundPrice()));
                    inboundQuantity.set(inboundQuantity.get() + detail.getInboundQuantity());
                    neededInboundQuantity.set(neededInboundQuantity.get() + detail.getNeedInboundQuantity());
                    quantity.set(inboundQuantity.get() + detail.getStockQuantity());
                })
                .collect(Collectors.toList());
        stockReportData.setItems(items);
        stockReportData.setStockQuantity(quantity.get());
        stockReportData.setInboundPrice(inboundPrice.get());
        stockReportData.setOutboundPrice(outboundPrice.get());
        stockReportData.setOutboundQuantity(outboundQuantity.get());
        stockReportData.setInboundQuantity(inboundQuantity.get());
        stockReportData.setTotalPrice(outboundPrice.get().subtract(inboundPrice.get()));
        stockReportData.setNeedInboundQuantity(neededInboundQuantity.get());
        return mapper.map(stockReportData, StockReportModelRes.class);
    }

    @Override
    public StockReportDetailModelRes createStockReportDetail(StockReportRequest stockReport) {
        Product product = productRepository.findById(stockReport.getProductId()).orElseThrow(() -> new IllegalArgumentException(""));
        List<InboundReportDetail> details = inboundReportDetailRepository.findByProductAndCreatedDateBetween(product,stockReport.getStartDate(), stockReport.getEndDate());
        AtomicReference<BigDecimal> inboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger inboundQuantity = new AtomicInteger();
        details.forEach(detail -> {
            inboundPrice.updateAndGet(v -> v.add(detail.getTotalPrice()));
            inboundQuantity.addAndGet(detail.getQuantity());
        });
        List<OutboundReportDetail> outboundDetails = outboundReportDetailRepository.findByProductAndCreatedDateBetween(product,stockReport.getStartDate(), stockReport.getEndDate());
        AtomicReference<BigDecimal> outboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger outboundQuantity = new AtomicInteger();
        outboundDetails.forEach(detail -> {
            outboundPrice.updateAndGet(v -> v.add(detail.getTotalPrice()));
            outboundQuantity.addAndGet(detail.getQuantity());
        });

        StockReportDetail stockReportDetail = StockReportDetail.builder()
                .product(product)
                .stockQuantity(product.getMaxQuantity())
                .inboundQuantity(inboundQuantity.get())
                .outboundQuantity(outboundQuantity.get())
                .inboundPrice(inboundPrice.get())
                .outboundPrice(outboundPrice.get())
                .build();
        return mapper.map(stockReportDetail, StockReportDetailModelRes.class);
    }


    @Override
    public StockReport getStockReportOfThisMonth() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the first and last date of the current month
        LocalDate firstDayOfThisMonth = currentDate.withDayOfMonth(1);
        LocalDate lastDayOfThisMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());

        // Convert LocalDate to Date (if needed)
        Date startDate = java.sql.Date.valueOf(firstDayOfThisMonth);
        Date endDate = java.sql.Date.valueOf(lastDayOfThisMonth);

        // Convert the start date to the start of the day (00:00:00)
        startDate = getStartOfDay(startDate);

        // Convert the end date to the end of the day (23:59:59)
        endDate = getEndOfDay(endDate);

        // Create a new StockReport object (without saving it to the database)
        StockReport stockReport = new StockReport();
        stockReport.setStartDate(startDate);
        stockReport.setEndDate(endDate);
        stockReport.setItems(new ArrayList<>());

        // Fetch all products
        List<Product> products = productRepository.findAll();

        // Initialize accumulators for the report summary
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger totalQuantity = new AtomicInteger();
        AtomicInteger neededInboundQuantity = new AtomicInteger();
        AtomicReference<BigDecimal> inboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger inboundQuantity = new AtomicInteger();
        AtomicReference<BigDecimal> outboundPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger outboundQuantity = new AtomicInteger();

        // Process each product to calculate details
        List<StockReportDetail> items = products.stream()
                .map(product -> createStockReportDetail(product, stockReport))
                .peek(detail -> {
                    outboundPrice.updateAndGet(v -> v.add(detail.getOutboundPrice()));
                    outboundQuantity.addAndGet(detail.getOutboundQuantity());
                    inboundPrice.updateAndGet(v -> v.add(detail.getInboundPrice()));
                    inboundQuantity.addAndGet(detail.getInboundQuantity());
                    neededInboundQuantity.addAndGet(detail.getNeedInboundQuantity());
                    totalQuantity.set(inboundQuantity.get() + detail.getStockQuantity());
                })
                .collect(Collectors.toList());

        // Populate the stock report object
        stockReport.setItems(items);
        stockReport.setStockQuantity(totalQuantity.get());
        stockReport.setInboundPrice(inboundPrice.get());
        stockReport.setOutboundPrice(outboundPrice.get());
        stockReport.setOutboundQuantity(outboundQuantity.get());
        stockReport.setInboundQuantity(inboundQuantity.get());
        stockReport.setTotalPrice(outboundPrice.get().subtract(inboundPrice.get()));
        stockReport.setNeedInboundQuantity(neededInboundQuantity.get());
        return  stockReport;
        // Map the StockReport object to the response model
    }

    public List<Map<String, Object>> getCategoryPercentages() {
        List<Product> products = productRepository.findAll();
        Map<String, Double> categoryTotals = new HashMap<>();
        double totalQuantity = 0;

        // Calculate total quantity and quantity per category
        for (Product product : products) {
            Category category = product.getCategory(); // Assuming Product has a getCategory method
            double currentCategoryQuantity = categoryTotals.getOrDefault(category.getName(), 0.0);
            categoryTotals.put(category.getName(), currentCategoryQuantity + product.getQuantity());
            totalQuantity += product.getQuantity(); // Accumulate the total quantity
        }

        // Prepare pieData format
        List<Map<String, Object>> pieData = new ArrayList<>();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            Map<String, Object> categoryData = new HashMap<>();
            categoryData.put("name", entry.getKey());
            categoryData.put("value", (entry.getValue() * 100.0) / totalQuantity);
            pieData.add(categoryData);
        }

        return pieData;
    }
    public List<ChartData> generateChartData() {
        Date today = new Date();
        List<ChartData> chartData = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            // Using Calendar to manipulate Date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            Date dayStart = getStartOfDay(calendar.getTime());
            Date dayEnd = getEndOfDay(calendar.getTime());

            List<InboundReportDetail> inboundDetails = inboundReportDetailRepository.findByCreatedDateBetween(
                    dayStart, dayEnd
            );
            List<OutboundReportDetail> outboundDetails = outboundReportDetailRepository.findByCreatedDateBetween(
                    dayStart, dayEnd
            );

            int inboundQuantity = inboundDetails.stream()
                    .mapToInt(InboundReportDetail::getQuantity)
                    .sum();
            int outboundQuantity = outboundDetails.stream()
                    .mapToInt(OutboundReportDetail::getQuantity)
                    .sum();

            String dayOfWeek = getDayOfWeek(dayStart); // Assuming you have a method to get abbreviated day name

            chartData.add(new ChartData(dayOfWeek, inboundQuantity, outboundQuantity));
        }

        return chartData;
    }

    @Override
    public List<StockReportModelRes> getStockReports() {
        return stockReportRepository.findAll().stream().map(item -> mapper.map(item, StockReportModelRes.class)).toList();
    }

    public List<ChartData> generateChartData(Date startDate, Date endDate) {
        List<ChartData> chartData = new ArrayList<>();

        // Calculate the number of days between start and end date
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        while (!startCalendar.after(endCalendar)) {
            // Get the start and end of the current day
            Date dayStart = getStartOfDay(startCalendar.getTime());
            Date dayEnd = getEndOfDay(startCalendar.getTime());

            // Fetch inbound and outbound report details for the day
            List<InboundReportDetail> inboundDetails = inboundReportDetailRepository.findByCreatedDateBetween(
                    dayStart, dayEnd
            );
            List<OutboundReportDetail> outboundDetails = outboundReportDetailRepository.findByCreatedDateBetween(
                    dayStart, dayEnd
            );

            // Calculate inbound and outbound quantities
            int inboundQuantity = inboundDetails.stream()
                    .mapToInt(InboundReportDetail::getQuantity)
                    .sum();
            int outboundQuantity = outboundDetails.stream()
                    .mapToInt(OutboundReportDetail::getQuantity)
                    .sum();

            // Get the day of the week as a label
            String dayOfWeek = getDayOfWeek(dayStart);

            // Add the data to the chart
            chartData.add(new ChartData(dayOfWeek, inboundQuantity, outboundQuantity));

            // Move to the next day
            startCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return chartData;
    }

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private String getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return "Mon";
            case Calendar.TUESDAY:
                return "Tue";
            case Calendar.WEDNESDAY:
                return "Wed";
            case Calendar.THURSDAY:
                return "Thu";
            case Calendar.FRIDAY:
                return "Fri";
            case Calendar.SATURDAY:
                return "Sat";
            case Calendar.SUNDAY:
                return "Sun";
            default:
                return "";
        }
    }
    @Transactional
    @Override
    public StockReport getStockReportOfLastMonth() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the first date of the previous month
        LocalDate firstDayOfLastMonth = currentDate.minusMonths(1).withDayOfMonth(1);

        // Get the last date of the previous month
        LocalDate lastDayOfLastMonth = currentDate.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        // Convert LocalDate to Date (if needed)
        Date startDate = java.sql.Date.valueOf(firstDayOfLastMonth);
        Date endDate = java.sql.Date.valueOf(lastDayOfLastMonth);

        // Convert the start date to the start of the day (00:00:00)
        startDate = getStartOfDay(startDate);

        // Convert the end date to the end of the day (23:59:59)
        endDate = getEndOfDay(endDate);

        // Fetch the stock report data for last month from the repository
        return stockReportRepository.findFirstByYearAndMonth(startDate, endDate);
    }

}
