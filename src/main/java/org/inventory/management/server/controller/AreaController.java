package org.inventory.management.server.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Area;
import org.inventory.management.server.model.area.AreaRequest;
import org.inventory.management.server.model.area.AreaRes;
import org.inventory.management.server.service.area.AreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/areas")
public class AreaController {
    private final AreaService areaService;

    @GetMapping
    public ResponseEntity<List<AreaRes>> getAll() {
        return ResponseEntity.ok(areaService.getAll());
    }
    @PostMapping
    public ResponseEntity<AreaRes> createArea(@Valid @RequestBody AreaRequest areaRequest) {
        return ResponseEntity.ok(areaService.createArea(areaRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AreaRes> updateArea(@PathVariable Long id, @Valid @RequestBody AreaRequest areaRequest){
        return ResponseEntity.ok(areaService.updateArea(id, areaRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<AreaRes> deleteArea(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.deleteArea(id));
    }
}