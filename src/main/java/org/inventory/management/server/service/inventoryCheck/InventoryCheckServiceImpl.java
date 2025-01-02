package org.inventory.management.server.service.inventoryCheck;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.*;
import org.inventory.management.server.model.employee.UserPrinciple;
import org.inventory.management.server.model.inventoryCheck.CreateInventoryCheckModel;
import org.inventory.management.server.model.inventoryCheck.InventoryCheckModelRes;
import org.inventory.management.server.model.inventoryCheck.ListDataRes;
import org.inventory.management.server.model.inventoryCheckDetail.CreateInventoryCheckDetailModel;
import org.inventory.management.server.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryCheckServiceImpl implements InventoryCheckService {
    private final InventoryCheckRepository inventoryCheckRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    @Override
    public InventoryCheckModelRes getInventoryCheckById(long id) {
        InventoryCheck InventoryCheck = inventoryCheckRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found InventoryCheck with id"+ id));
       return modelMapper.map(InventoryCheck, InventoryCheckModelRes.class);
    }

    @Override
    public ListDataRes<InventoryCheckModelRes> getInventoryChecks() {
        List<InventoryCheck> items = inventoryCheckRepository.findAll();
        ListDataRes<InventoryCheckModelRes> res = new ListDataRes<>();
        res.setData(items.stream().map(item -> modelMapper.map(item, InventoryCheckModelRes.class)).toList());
        res.setTotal(items.toArray().length);
        return res;
    }

    private InventoryCheckDetail createInventoryCheckDetail(CreateInventoryCheckDetailModel item, InventoryCheck inventoryCheck) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Not found Product with id: " + item.getProductId()));
        if (item.getLoss() > product.getQuantity()) {
            throw new IllegalArgumentException("Loss exceeds available stock for product id: " + item.getProductId());
        }

        InventoryCheckDetail detail = modelMapper.map(item, InventoryCheckDetail.class);
        detail.setProduct(product);
        detail.setStock(product.getQuantity());
        detail.setLoss(item.getLoss());
        detail.setActualQuantity(product.getQuantity() - item.getLoss());
        detail.setInventoryCheck(inventoryCheck);
        detail.setCreatedDate(inventoryCheck.getCreatedDate());
        product.setQuantity(product.getQuantity() - item.getLoss());
        BigDecimal subTotal = product.getProductionCost().multiply(BigDecimal.valueOf(item.getLoss()));
        detail.setLossValue(subTotal);
        productRepository.save(product);
        return detail;
    }

    @Transactional
    @Override
    public InventoryCheckModelRes createInventoryCheck(CreateInventoryCheckModel inventoryCheckModel) {
        InventoryCheck inventoryCheck = modelMapper.map(inventoryCheckModel, InventoryCheck.class);
        Long userId = ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        Employee employee = employeeRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found employee"));
        inventoryCheck.setItems(new ArrayList<>());
        inventoryCheck.setEmployee(employee);
        inventoryCheck.setCreatedDate(inventoryCheckModel.getDate());
        InventoryCheck inventoryCheckData = inventoryCheckRepository.save(inventoryCheck);
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        List<InventoryCheckDetail> items = inventoryCheckModel.getItems().stream()
                .map(item -> createInventoryCheckDetail(item, inventoryCheckData))
                .peek(detail -> {
                    totalPrice.updateAndGet(v -> v.add(detail.getLossValue()));
                })
                .collect(Collectors.toList());
        inventoryCheckData.setItems(items);
        inventoryCheckData.setTotalPrice(totalPrice.get());
        inventoryCheckRepository.save(inventoryCheckData);
        return modelMapper.map(inventoryCheckData, InventoryCheckModelRes.class);
    }


    @Override
    public InventoryCheckModelRes deleteInventoryCheck(long id)  {
        InventoryCheckModelRes inventoryCheck = getInventoryCheckById(id);
        if(inventoryCheck.getItems().isEmpty()){
            inventoryCheckRepository.deleteById(id);
            return inventoryCheck;
        }
            throw new IllegalArgumentException("Can not delete outbound report, contains inbound report details");
    }
}
