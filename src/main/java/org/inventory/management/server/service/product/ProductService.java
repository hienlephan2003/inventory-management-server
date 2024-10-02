package org.inventory.management.server.service.product;

import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.model.product.UpsertProductModel;

public interface ProductService {
    ProductModelRes getProductById(long id) ;
    ProductModelRes upsertProduct(UpsertProductModel productModel);
    ProductModelRes deleteProduct(long id);
}
