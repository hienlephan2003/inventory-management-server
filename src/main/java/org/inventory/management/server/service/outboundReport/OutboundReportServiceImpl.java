package org.inventory.management.server.service.outboundReport;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.*;
import org.inventory.management.server.model.outboundReport.OutboundReportModelRes;
import org.inventory.management.server.model.outboundReport.UpsertOutboundReportModel;
import org.inventory.management.server.model.outboundReportDetail.UpsertOutboundReportDetailModel;
import org.inventory.management.server.repository.*;
import org.inventory.management.server.service.inboundReport.InboundReportService;
import org.inventory.management.server.service.stockDetail.StockReportDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    @Override
    public OutboundReportModelRes getOutboundReportById(long id) {
        OutboundReport OutboundReport = outboundReportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found OutboundReport with id"+ id));
       return modelMapper.map(OutboundReport, OutboundReportModelRes.class);
    }
    private OutboundReportDetail createOutboundReportDetail(UpsertOutboundReportDetailModel item, OutboundReport outboundReport) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Not found Product with id: " + item.getProductId()));

        OutboundReportDetail detail = modelMapper.map(item, OutboundReportDetail.class);
        detail.setProduct(product);
        detail.setOutboundReport(outboundReport);
        BigDecimal subTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        detail.setTotalPrice(subTotal);
        stockReportDetailService.onOutboundReport(detail);
        inboundReportService.updateStockQuantity(detail);
        return detail;
    }

    @Transactional
    @Override
    public OutboundReportModelRes createOutboundReport(UpsertOutboundReportModel outboundReportModel) {
        OutboundReport outboundReport = modelMapper.map(outboundReportModel, OutboundReport.class);
        Employee employee = employeeRepository.findById(outboundReportModel.getShipment().getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("Not found employee"));
        Shipment shipmentRequest = (outboundReport.getShipment());
        shipmentRequest.setPic(employee);
        Shipment shipment = shipmentRepository.save(shipmentRequest);
        outboundReport.setShipment(shipment);
        outboundReport.setItems(new ArrayList<>());
        OutboundReport outboundReportData = outboundReportRepository.save(outboundReport);
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OutboundReportDetail> items = outboundReportModel.getItems().stream()
                .map(item -> createOutboundReportDetail(item, outboundReportData))
                .peek(detail -> {
                    totalPrice.add(detail.getTotalPrice());
                })
                .collect(Collectors.toList());
        outboundReportData.setItems(items);
        outboundReportData.setTotalPrice(totalPrice);
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

        BigDecimal totalPrice = BigDecimal.ZERO;
        if (outboundReportModel.getItems() != null && !outboundReportModel.getItems().isEmpty()) {
            if (outboundReport.getItems() != null) {
                outboundReportDetailRepository.deleteAll(outboundReport.getItems());
                outboundReport.getItems().clear();
            }
            List<OutboundReportDetail> items = outboundReportModel.getItems().stream()
                    .map(item -> createOutboundReportDetail(item, outboundReport))
                    .peek(detail -> totalPrice.add(detail.getTotalPrice()))
                    .collect(Collectors.toList());
            outboundReport.setItems(items);
        }

        outboundReport.setTotalPrice(totalPrice);

        outboundReportRepository.save(outboundReport);

        return modelMapper.map(outboundReport, OutboundReportModelRes.class);
    }


    @Override
    public OutboundReportModelRes deleteOutboundReport(long id) {
        OutboundReportModelRes outboundReport = getOutboundReportById(id);
        outboundReportRepository.deleteById(id);
        return outboundReport;
    }
}
