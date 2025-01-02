package org.inventory.management.server.service.stockDetail;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.*;
import org.inventory.management.server.repository.*;
import org.inventory.management.server.service.stock.StockReportService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class StockReportDetailServiceImpl implements StockReportDetailService {
    private final ProductRepository productRepository;
    private final StockReportDetailRepository stockReportDetailRepository;
    private final StockReportService stockReportService;

    private StockReportDetail upsertStockReportDetailOfMonth(Product product) {
        StockReport stockReport = stockReportService.getStockReportOfThisMonth();
        Optional<StockReportDetail> lastStockReport = stockReportDetailRepository.findFirstByProductOrderByCreatedDate(product);
        if(lastStockReport.isEmpty()){

            StockReportDetail stockReportDetail = StockReportDetail.builder()
                    .stockReport(stockReport)
                    .createdDate(new Date())
//                    .expiredQuantity(0)
                    .outboundQuantity(0)
//                    .quantity(0)
                    .product(product)
                    .build();
            return stockReportDetailRepository.save(stockReportDetail);
        }
        else{
            StockReportDetail lastStockReportDetail = lastStockReport.get();
            StockReportDetail stockReportDetail = StockReportDetail.builder()
                    .stockReport(stockReport)
                    .createdDate(new Date())
//                    .expiredQuantity(0)
                    .outboundQuantity(0)
//                    .quantity(lastStockReportDetail.getQuantity())
                    .product(product)
                    .build();
            return stockReportDetailRepository.save(stockReportDetail);

        }
    }


    @Override
    public StockReportDetail getStockReportDetailOfMonth(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("Not found product by id " + productId));

        Optional<StockReportDetail> stockReportDetail = stockReportDetailRepository.findFirstByProductOrderByCreatedDate(
                product
        );
        if(stockReportDetail.isPresent() && stockReportDetail.get().getCreatedDate().getMonth() == new Date().getMonth()
         && stockReportDetail.get().getCreatedDate().getYear() == new Date().getYear()
        ){
            return stockReportDetail.get();
        }
        else{
            return upsertStockReportDetailOfMonth(product);
        }
    }
    @Transactional
    @Override
    public StockReportDetail onInboundReport(InboundReportDetail inboundReportDetail) {
//        StockReportDetail stockReportDetail = getStockReportDetailOfMonth(inboundReportDetail.getProduct().getId());
//        int currentQuantity = stockReportDetail.getQuantity();
//        stockReportDetail.setQuantity(currentQuantity + inboundReportDetail.getQuantity());
//        return stockReportDetailRepository.save(stockReportDetail);
        return null;
    }
    @Transactional
    @Override
    public StockReportDetail onOutboundReport(OutboundReportDetail outboundReportDetail) {
        return  null;
//        StockReportDetail stockReportDetail = getStockReportDetailOfMonth(outboundReportDetail.getProduct().getId());
//        if(outboundReportDetail.getIsExpired()){
//            stockReportDetail.setExpiredQuantity(stockReportDetail.getExpiredQuantity() + outboundReportDetail.getQuantity());
//            if(stockReportDetail.getQuantity() - outboundReportDetail.getQuantity() < 0){
//                throw new IllegalArgumentException("Not enough stock for this outbound for product with id" + outboundReportDetail.getProduct().getId());
//            }
//            stockReportDetail.setQuantity(stockReportDetail.getQuantity() - outboundReportDetail.getQuantity());
//            return stockReportDetailRepository.save(stockReportDetail);
//        }
//        else{
//            stockReportDetail.setOutboundQuantity(stockReportDetail.getOutboundQuantity() + outboundReportDetail.getQuantity());
//            if(stockReportDetail.getQuantity() - outboundReportDetail.getQuantity() < 0){
//                throw new IllegalArgumentException("Not enough stock for this outbound for product with id" + outboundReportDetail.getProduct().getId());
//            }
//            stockReportDetail.setQuantity(stockReportDetail.getQuantity() - outboundReportDetail.getQuantity());
//            return stockReportDetailRepository.save(stockReportDetail);
//        }
    }
}
