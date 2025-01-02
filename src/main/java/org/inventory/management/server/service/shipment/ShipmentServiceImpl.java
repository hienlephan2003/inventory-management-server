package org.inventory.management.server.service.shipment;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Shipment;
import org.inventory.management.server.model.query.ListQueryParam;
import org.inventory.management.server.model.shipment.ListShipmentModelRes;
import org.inventory.management.server.model.shipment.ShipmentModelRes;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;
import org.inventory.management.server.repository.ShipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements  ShipmentService {
    private final ShipmentRepository shipmentRepository;
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
        shipment.setStatus(shipmentModel.getStatus());
        shipment.setCompletedDate(shipmentModel.getCompletedDate());
        Shipment shipmentData = shipmentRepository.save(shipment);
        return mapper.map(shipmentData, ShipmentModelRes.class);
    }
}
