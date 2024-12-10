package org.inventory.management.server.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inventory.management.server.model.query.ListQueryParam;
import org.inventory.management.server.model.shipment.ListShipmentModelRes;
import org.inventory.management.server.model.shipment.ShipmentModelRes;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;
import org.inventory.management.server.service.shipment.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController()
@RequestMapping("/api/shipments")
@AllArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;

    @PostMapping("/list")
    ResponseEntity<ListShipmentModelRes> getShipments(@Valid @RequestBody ListQueryParam params){
        return ResponseEntity.ok(shipmentService.getShipments(params));
    }
    @PutMapping("/{id}")
    ResponseEntity<ShipmentModelRes> updateShipment(@PathVariable Long id, @Valid @RequestBody UpsertShipmentModel shipmentModel){
        return ResponseEntity.ok(shipmentService.updateShipment(id, shipmentModel));
    }
}
