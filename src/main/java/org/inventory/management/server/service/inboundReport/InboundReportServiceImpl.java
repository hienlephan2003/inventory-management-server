package org.inventory.management.server.service.inboundReport;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.*;
import org.inventory.management.server.model.inboundReport.CreateInboundReportModel;
import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.UpdateInboundReportModel;
import org.inventory.management.server.model.inboundReportDetail.CreateInboundReportDetailModel;
import org.inventory.management.server.model.inboundReportDetail.InboundReportDetailModelRes;
import org.inventory.management.server.model.inboundReportDetail.UpdateInboundReportDetailModel;
import org.inventory.management.server.repository.*;
import org.inventory.management.server.service.stockDetail.StockReportDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboundReportServiceImpl implements InboundReportService {
    private final InboundReportRepository inboundReportRepository;
    private final InboundReportDetailRepository inboundReportDetailRepository;
    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final StockReportDetailService stockReportDetailService;
    @Override
    public InboundReportModelRes getInboundReportById(long id) {
        InboundReport InboundReport = inboundReportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found InboundReport with id"+ id));
       return modelMapper.map(InboundReport, InboundReportModelRes.class);
    }
    private InboundReportDetail createInboundReportDetail(CreateInboundReportDetailModel item, InboundReport inboundReport) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Not found Product with id: " + item.getProductId()));

        InboundReportDetail detail = modelMapper.map(item, InboundReportDetail.class);
        detail.setProduct(product);
        detail.setStockQuantity(item.getQuantity());
        detail.setInboundReport(inboundReport);
        stockReportDetailService.onInboundReport(detail);
        BigDecimal subTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        detail.setTotalPrice(subTotal);
        return detail;
    }
    private InboundReportDetail updateInboundReportDetail(UpdateInboundReportDetailModel item, InboundReport inboundReport)  {
        InboundReportDetail detail = modelMapper.map(item, InboundReportDetail.class);
        int outboundQuantity = detail.getQuantity() - detail.getStockQuantity();
        if(outboundQuantity < item.getQuantity()){
            throw new IllegalArgumentException( "Can not update inbound report detail");
        }
        detail.setUnitPrice(item.getUnitPrice());
        detail.setQuantity(item.getQuantity());
        detail.setStockQuantity(item.getQuantity() - outboundQuantity);
        detail.setStockQuantity(item.getQuantity());
        detail.setInboundReport(inboundReport);
        stockReportDetailService.onInboundReport(detail);
        BigDecimal subTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        detail.setTotalPrice(subTotal);
        return inboundReportDetailRepository.save(detail);
    }

    @Transactional
    @Override
    public InboundReportModelRes createInboundReport(CreateInboundReportModel inboundReportModel) {
        InboundReport inboundReport = modelMapper.map(inboundReportModel, InboundReport.class);
        Employee employee = employeeRepository.findById(inboundReportModel.getShipment().getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("Not found employee"));
        Shipment shipmentRequest = (inboundReport.getShipment());
        shipmentRequest.setPic(employee);
        Shipment shipment = shipmentRepository.save(shipmentRequest);
        inboundReport.setShipment(shipment);
        inboundReport.setItems(new ArrayList<>());
        InboundReport inboundReportData = inboundReportRepository.save(inboundReport);
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<InboundReportDetail> items = inboundReportModel.getItems().stream()
                .map(item -> createInboundReportDetail(item, inboundReportData))
                .peek(detail -> {
                    totalPrice.add(detail.getTotalPrice());
                })
                .collect(Collectors.toList());
        inboundReportData.setItems(items);
        inboundReportData.setPrice(totalPrice);
        inboundReportRepository.save(inboundReportData);
        return modelMapper.map(inboundReportData, InboundReportModelRes.class);
    }
    @Override
    @Transactional
    public InboundReportModelRes updateInboundReport(long id, UpdateInboundReportModel inboundReportModel) {
        InboundReport inboundReport = inboundReportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InboundReport not found with id " + id));

        modelMapper.map(inboundReportModel, inboundReport);

        if (inboundReportModel.getShipment() != null && inboundReportModel.getShipment().getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(inboundReportModel.getShipment().getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + inboundReportModel.getShipment().getEmployeeId()));
            inboundReport.getShipment().setPic(employee);
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        if (inboundReportModel.getItems() != null && !inboundReportModel.getItems().isEmpty()) {
            List<InboundReportDetail> items = inboundReportModel.getItems().stream()
                    .map(item -> {
                        return updateInboundReportDetail(item, inboundReport);
                    })
                    .peek(detail -> totalPrice.add(detail.getTotalPrice()))
                    .collect(Collectors.toList());
        }

        inboundReport.setPrice(totalPrice);

        inboundReportRepository.save(inboundReport);

        return modelMapper.map(inboundReport, InboundReportModelRes.class);
    }


    @Override
    public InboundReportModelRes deleteInboundReport(long id)  {
        InboundReportModelRes inboundReport = getInboundReportById(id);
        if(inboundReport.getItems().isEmpty()){
            inboundReportRepository.deleteById(id);
            return inboundReport;
        }
            throw new IllegalArgumentException("Can not delete outbound report, contains inbound report details");
    }

    @Override
    @Transactional
    public List<InboundReportDetailModelRes> updateStockQuantity(OutboundReportDetail outboundReportDetail) {
        AtomicInteger quantity = new AtomicInteger(outboundReportDetail.getQuantity());

        List<InboundReportDetail> inboundReportDetails = inboundReportDetailRepository.findByProductAndExpirationDateAfterAndStockQuantityGreaterThanOrderByExpirationDateAsc(
                outboundReportDetail.getProduct(),
                new Date(),
                0
        );
        List<InboundReportDetailModelRes> updatedDetails = new ArrayList<>();

        for (InboundReportDetail item : inboundReportDetails) {
            int currentStock = item.getStockQuantity();
            if (currentStock <= quantity.get()) {
                quantity.addAndGet(-currentStock);
                item.setStockQuantity(0);
            } else {
                item.setStockQuantity(currentStock - quantity.get());
                quantity.set(0);
                break;
            }
            updatedDetails.add(modelMapper.map(inboundReportDetailRepository.save(item), InboundReportDetailModelRes.class));
        }
        if(quantity.get() > 0){
            throw  new IllegalArgumentException("Not enough stock for outbound report detail with product id" + outboundReportDetail.getProduct().getId());
        }
        return updatedDetails;
    }
}
