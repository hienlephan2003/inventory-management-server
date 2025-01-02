package org.inventory.management.server.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inventory.management.server.model.inventoryCheck.CreateInventoryCheckModel;
import org.inventory.management.server.model.inventoryCheck.InventoryCheckModelRes;
import org.inventory.management.server.model.inventoryCheck.ListDataRes;
import org.inventory.management.server.service.inventoryCheck.InventoryCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/inventoryChecks")
@AllArgsConstructor
public class InventoryCheckController {
    private final InventoryCheckService inventoryCheckService;
    @GetMapping("/{id}")
    ResponseEntity<InventoryCheckModelRes> getInventoryCheckById(@PathVariable Long id){
        return ResponseEntity.ok(inventoryCheckService.getInventoryCheckById(id));
    }
    @GetMapping("")
    ResponseEntity<ListDataRes<InventoryCheckModelRes>> getInventoryChecks(){
        return ResponseEntity.ok(inventoryCheckService.getInventoryChecks());
    }
    @PostMapping
    ResponseEntity<InventoryCheckModelRes> createInventoryCheck(@Valid @RequestBody CreateInventoryCheckModel inventoryCheckModel){
        return ResponseEntity.ok(inventoryCheckService.createInventoryCheck(inventoryCheckModel));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<InventoryCheckModelRes> deleteInventoryCheck(@PathVariable Long id) {

        return ResponseEntity.ok(inventoryCheckService.deleteInventoryCheck(id));
    }
}
