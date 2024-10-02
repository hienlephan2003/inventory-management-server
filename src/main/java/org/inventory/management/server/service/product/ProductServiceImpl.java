package org.inventory.management.server.service.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.model.product.UpsertProductModel;
import org.inventory.management.server.repository.ProductJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductJpaRepository productJpaRepository;
    private final ModelMapper modelMapper;
    @Override
    public ProductModelRes getProductById(long id) {
        Product product = productJpaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found product with id"+ id));
       return modelMapper.map(product, ProductModelRes.class);
    }

    @Override
    public ProductModelRes upsertProduct(UpsertProductModel productModel) {
        Product product = modelMapper.map(productModel, Product.class);
        productJpaRepository.save(product);
        return modelMapper.map(product, ProductModelRes.class);
    }

    @Override
    public ProductModelRes deleteProduct(long id) {
        return null;
    }
}
