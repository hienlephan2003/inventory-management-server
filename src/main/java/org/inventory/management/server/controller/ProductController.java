package org.inventory.management.server.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inventory.management.server.model.product.ListProductRes;
import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.model.product.UpsertProductModel;
import org.inventory.management.server.model.query.ListQueryParam;
import org.inventory.management.server.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController()
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/{id}")
    ResponseEntity<ProductModelRes> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PostMapping()
    ResponseEntity<ProductModelRes> addProduct( @Valid @RequestBody UpsertProductModel productModel){
        return ResponseEntity.ok(productService.upsertProduct(null, productModel));
    }
    @PutMapping("/{id}")
    ResponseEntity<ProductModelRes> updateProduct(@PathVariable Long id, @Valid @RequestBody UpsertProductModel productModel){
        return ResponseEntity.ok(productService.upsertProduct(id, productModel));
    }
    @PostMapping("/list")
    ResponseEntity<ListProductRes> getProducts(@Valid @RequestBody ListQueryParam params){
        return ResponseEntity.ok(productService.getProducts(params));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductModelRes> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
