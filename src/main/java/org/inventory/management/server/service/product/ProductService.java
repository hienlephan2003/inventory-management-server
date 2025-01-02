package org.inventory.management.server.service.product;

import org.inventory.management.server.model.product.ListProductRes;
import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.model.product.UpsertProductModel;
import org.inventory.management.server.model.query.ListQueryParam;

import java.util.Optional;

public interface ProductService {
    ProductModelRes getProductById(long id) ;
    ProductModelRes upsertProduct(Long id, UpsertProductModel productModel);
    ProductModelRes updateProduct(Long id, UpsertProductModel productModel);
    ProductModelRes deleteProduct(long id);
    ListProductRes getProducts(ListQueryParam params);
    ListProductRes getAllProducts();
    ListProductRes getNeedInboundProducts();
}
