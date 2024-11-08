package org.inventory.management.server.service.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Category;
import org.inventory.management.server.entity.Company;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.model.product.ListProductParams;
import org.inventory.management.server.model.product.ListProductRes;
import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.model.product.UpsertProductModel;
import org.inventory.management.server.repository.CategoryRepository;
import org.inventory.management.server.repository.CompanyRepository;
import org.inventory.management.server.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    @Override
    public ProductModelRes getProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found product with id"+ id));
       return modelMapper.map(product, ProductModelRes.class);
    }

    @Override
    public ProductModelRes upsertProduct(Long id, UpsertProductModel productModel) {
        Product product = modelMapper.map(productModel, Product.class);
        if(id != null) {
            product.setId(id);
        }
        Category category = categoryRepository.findById(productModel.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Not found category with id"+ productModel.getCategoryId()));
        Company company = companyRepository.findById(productModel.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Not found company with id"+ productModel.getCompanyId()));
        product.setCategory(category);
        product.setCompany(company);
        productRepository.save(product);
        return modelMapper.map(product, ProductModelRes.class);
    }

    @Override
    public ProductModelRes deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found product with id"+ id));
        productRepository.delete(product);
        return modelMapper.map(product, ProductModelRes.class);
    }

    @Override
    public ListProductRes getProducts(ListProductParams params) {
        Pageable paging = PageRequest.of(params.getPageNumber(), params.getPageSize());
        String query = params.getQuery();
        Page<Product> pagedResult;
        if ( query != null && !query.isEmpty() ) {
            // Assuming productRepository supports a method like this using a custom query or Specification
            pagedResult = productRepository.findByNameContainingIgnoreCase(query, paging);
        } else {
            pagedResult = productRepository.findAll(paging);
        }
        ListProductRes listProductRes = new ListProductRes();
        listProductRes.setProductList(pagedResult.getContent());
        listProductRes.setTotal(pagedResult.getContent().size());
        return listProductRes;
    }
}
