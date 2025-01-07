package org.inventory.management.server.service.shipment;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.inventory.management.server.entity.Shipment;
import org.inventory.management.server.model.enumeratiion.ShipmentStatus;
import org.inventory.management.server.model.enumeratiion.ShipmentType;
import org.inventory.management.server.model.query.ListQueryParam;
import org.inventory.management.server.model.shipment.ListShipmentModelRes;
import org.inventory.management.server.model.shipment.ShipmentModelRes;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;
import org.inventory.management.server.repository.ShipmentRepository;
import org.inventory.management.server.service.inboundReport.InboundReportService;
import org.inventory.management.server.service.outboundReport.OutboundReportService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements  ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final InboundReportService inboundReportService;
    private final OutboundReportService outboundReportService;
    private final ModelMapper mapper;

    @Override
    public ListShipmentModelRes getShipments(ListQueryParam params) {
        Pageable paging = PageRequest.of(params.getPageNumber(), params.getPageSize());
        Page<Shipment> pagedResult;
        pagedResult = shipmentRepository.findAll(paging);
        ListShipmentModelRes listShipmentRes = new ListShipmentModelRes();
        listShipmentRes.setShipmentList(pagedResult.getContent());
        listShipmentRes.setTotal(pagedResult.getContent().size());
        return listShipmentRes;
    }

    @Override
    public ShipmentModelRes updateShipment(Long shipmentId, UpsertShipmentModel shipmentModel) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow( () -> new EntityNotFoundException("Not found shipment with id " + shipmentId));
        if(shipment.getStatus() == ShipmentStatus.COMPLETED && shipment.getType() == ShipmentType.INBOUND){
            throw new IllegalArgumentException("Can not update completed inbound shipment");
        }
        if(shipment.getStatus() == ShipmentStatus.CANCELLED && shipment.getType() == ShipmentType.OUTBOUND){
            throw new IllegalArgumentException("Can not update cancelled outbound shipment");
        }

        shipment.setStatus(shipmentModel.getStatus());
        shipment.setCompletedDate(shipmentModel.getCompletedDate());
        Shipment shipmentData = shipmentRepository.save(shipment);

        if(shipmentData.getStatus() == ShipmentStatus.COMPLETED && shipmentData.getType() == ShipmentType.INBOUND){
            inboundReportService.onShipmentSuccess(shipmentData);
        }
        else if(shipmentData.getStatus() == ShipmentStatus.CANCELLED && shipmentData.getType() == ShipmentType.OUTBOUND){
            outboundReportService.onShipmentCancel(shipmentData);
        }
        return mapper.map(shipmentData, ShipmentModelRes.class);
    }
}
