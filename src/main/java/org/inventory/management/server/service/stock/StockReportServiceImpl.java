package org.inventory.management.server.service.stock;

import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.StockReport;
import org.inventory.management.server.repository.StockReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockReportServiceImpl implements StockReportService {
    private final StockReportRepository stockReportRepository;
    private final ModelMapper mapper;
    @Override
    public StockReport getStockReportOfMonth() {
      Optional<StockReport> stockReport = stockReportRepository.findFirstByOrderByDateAsc();
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
}
