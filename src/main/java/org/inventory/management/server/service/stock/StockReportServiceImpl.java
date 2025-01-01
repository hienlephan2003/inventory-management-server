package org.inventory.management.server.service.stock;

import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Category;
import org.inventory.management.server.entity.StockReport;
import org.inventory.management.server.entity.StockReportDetail;
import org.inventory.management.server.repository.StockReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockReportServiceImpl implements StockReportService {
    private final StockReportRepository stockReportRepository;
    private final ModelMapper mapper;
    @Override
    public StockReport getStockReportOfMonth() {
      Optional<StockReport> stockReport = stockReportRepository.findFirstByOrderByDateDesc();
      if(stockReport.isPresent() && stockReport.get().getDate().getMonth() == new Date().getMonth()
              && stockReport.get().getDate().getYear() == new Date().getYear()) {
          return stockReport.get();
      }else{
          StockReport newStockReport = StockReport.builder()
                  .date(new Date())
                  .items(new ArrayList<>())
                  .build();
          return stockReportRepository.save(newStockReport);
      }
    }
    public Map<String, Double> getCategoryPercentages() {
        List<StockReportDetail> stockDetails = getStockReportOfMonth().getItems();

        Map<String, Double> categoryTotals = new HashMap<>();
        double totalQuantity = 0;

        // Calculate total quantity of all products
        for (StockReportDetail detail : stockDetails) {
            totalQuantity += detail.getQuantity();
        }

        // Calculate quantity per category
        for (StockReportDetail detail : stockDetails) {
            Category category = detail.getProduct().getCategory(); // Assuming Product has a getCategory method
            double currentCategoryQuantity = categoryTotals.getOrDefault(category.getName(), 0.0);
            categoryTotals.put(category.getName(), currentCategoryQuantity + detail.getQuantity());
        }

        // Calculate the percentage for each category
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalQuantity;
            categoryTotals.put(entry.getKey(), percentage);
        }

        return categoryTotals;
    }

}
