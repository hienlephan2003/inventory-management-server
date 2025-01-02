package org.inventory.management.server.service.outboundReport;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.*;
import org.inventory.management.server.model.enumeratiion.ShipmentType;
import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.ListDataRes;
import org.inventory.management.server.model.outboundReport.OutboundReportModelRes;
import org.inventory.management.server.model.outboundReport.UpsertOutboundReportModel;
import org.inventory.management.server.model.outboundReportDetail.UpsertOutboundReportDetailModel;
import org.inventory.management.server.repository.*;
import org.inventory.management.server.service.inboundReport.InboundReportService;
import org.inventory.management.server.service.stockDetail.StockReportDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutboundReportServiceImpl implements OutboundReportService {
    private final OutboundReportRepository outboundReportRepository;
    private final OutboundReportDetailRepository outboundReportDetailRepository;
    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final StockReportDetailService stockReportDetailService;
    private final InboundReportService inboundReportService;
    private final StockReportDetailRepository stockReportDetailRepository;

    @Override
    public ListDataRes<OutboundReportModelRes> getOutboundReports() {
        List<OutboundReport> items = outboundReportRepository.findAll();
        ListDataRes<OutboundReportModelRes> res = new ListDataRes<>();
        res.setData(items.stream().map(item -> modelMapper.map(item, OutboundReportModelRes.class)).toList());
        res.setTotal(items.toArray().length);
        return res;
    }

    @Override
    public OutboundReportModelRes getOutboundReportById(long id) {
        OutboundReport OutboundReport = outboundReportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found OutboundReport with id"+ id));
       return modelMapper.map(OutboundReport, OutboundReportModelRes.class);
    }
    private OutboundReportDetail createOutboundReportDetail(UpsertOutboundReportDetailModel item, OutboundReport outboundReport) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Not found Product with id: " + item.getProductId()));
        if(product.getQuantity() - item.getQuantity() < 0){
            throw new IllegalArgumentException("Not enough stock for this outbound for product with id" + product.getId());
        }

        OutboundReportDetail detail = modelMapper.map(item, OutboundReportDetail.class);
        detail.setProduct(product);
        product.setQuantity(product.getQuantity() - item.getQuantity());
        detail.setOutboundReport(outboundReport);
        BigDecimal subTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        detail.setTotalPrice(subTotal);
        OutboundReportDetail detailData = outboundReportDetailRepository.save(detail);
        stockReportDetailService.onOutboundReport(detailData);
        List<OutboundLineItem> inbounds =  inboundReportService.updateStockQuantity(detailData);
        detail.setInbounds(inbounds);
        return detailData;
    }

    @Transactional
    @Override
    public OutboundReportModelRes createOutboundReport(UpsertOutboundReportModel outboundReportModel) {
        OutboundReport outboundReport = modelMapper.map(outboundReportModel, OutboundReport.class);
        Employee employee = employeeRepository.findById(outboundReportModel.getShipment().getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("Not found employee"));
        Shipment shipmentRequest = (outboundReport.getShipment());
        shipmentRequest.setType(ShipmentType.OUTBOUND);
        shipmentRequest.setPic(employee);
        Shipment shipment = shipmentRepository.save(shipmentRequest);
        outboundReport.setShipment(shipment);
        outboundReport.setItems(new ArrayList<>());
        OutboundReport outboundReportData = outboundReportRepository.save(outboundReport);
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger quantity = new AtomicInteger();
        List<OutboundReportDetail> items = outboundReportModel.getItems().stream()
                .map(item -> createOutboundReportDetail(item, outboundReportData))
                .peek(detail -> {
                    totalPrice.updateAndGet(v -> v.add(detail.getTotalPrice()));
                    quantity.set(quantity.get() + detail.getQuantity());
                })
                .collect(Collectors.toList());
        outboundReportData.setItems(items);
        outboundReportData.setTotalPrice(totalPrice.get());
        outboundReportData.setQuantity(quantity.get());
        outboundReportRepository.save(outboundReportData);
        return modelMapper.map(outboundReportData, OutboundReportModelRes.class);
    }
    @Override
    @Transactional
    public OutboundReportModelRes updateOutboundReport(long id, UpsertOutboundReportModel outboundReportModel) {
        OutboundReport outboundReport = outboundReportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OutboundReport not found with id " + id));

        modelMapper.map(outboundReportModel, outboundReport);

        if (outboundReportModel.getShipment() != null && outboundReportModel.getShipment().getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(outboundReportModel.getShipment().getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + outboundReportModel.getShipment().getEmployeeId()));
            outboundReport.getShipment().setPic(employee);
        }

        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        if (outboundReportModel.getItems() != null && !outboundReportModel.getItems().isEmpty()) {
            if (outboundReport.getItems() != null) {
                outboundReportDetailRepository.deleteAll(outboundReport.getItems());
                outboundReport.getItems().clear();
            }
            List<OutboundReportDetail> items = outboundReportModel.getItems().stream()
                    .map(item -> createOutboundReportDetail(item, outboundReport))
                    .peek(detail ->
                            totalPrice.updateAndGet(v -> v.add(detail.getTotalPrice()))
                        )
                    .collect(Collectors.toList());
            outboundReport.setItems(items);
        }

        outboundReport.setTotalPrice(totalPrice.get());

        outboundReportRepository.save(outboundReport);

        return modelMapper.map(outboundReport, OutboundReportModelRes.class);
    }


    @Override
    public OutboundReportModelRes deleteOutboundReport(long id) {
        OutboundReportModelRes outboundReport = getOutboundReportById(id);
        outboundReportRepository.deleteById(id);
        return outboundReport;
    }

    @Override
    public Map<String, Integer> getCurrentWeekQuantitiesByDate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);

        Date startDate = Date.from(startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfWeek.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        List<OutboundReport> reports = outboundReportRepository.findAllByDateBetween(startDate, endDate);

        Map<String, Integer> quantitiesByDate = new HashMap<>();
        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            String dayOfWeek = date.getDayOfWeek().toString();
            quantitiesByDate.put(dayOfWeek, 0);
        }

        for (OutboundReport report : reports) {
            LocalDate reportDate = report.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String dayOfWeek = reportDate.getDayOfWeek().toString();
            quantitiesByDate.put(dayOfWeek, quantitiesByDate.getOrDefault(dayOfWeek, 0) + report.getQuantity());
        }
        return quantitiesByDate;
    }
}
