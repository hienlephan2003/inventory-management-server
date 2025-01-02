package org.inventory.management.server.service.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.*;
import org.inventory.management.server.model.inboundReportDetail.InboundReportDetailModelRes;
import org.inventory.management.server.model.product.ListProductRes;
import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.model.product.UpsertProductModel;
import org.inventory.management.server.model.query.ListQueryParam;
import org.inventory.management.server.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final InboundReportDetailRepository inboundReportDetailRepository;
    private final ModelMapper modelMapper;
    private final StockReportDetailRepository stockReportDetailRepository;
    @Override
    public ProductModelRes getProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found product with id"+ id));
        List<InboundReportDetail> items = inboundReportDetailRepository.findByProductAndExpirationDateAfterAndStockQuantityGreaterThanOrderByExpirationDateAsc(
                product,
                new Date(),
                0
        );
       ProductModelRes res = modelMapper.map(product, ProductModelRes.class);
       res.setItems(items.stream().map(item -> modelMapper.map(item, InboundReportDetailModelRes.class)).toList());
       return res;
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
        Set<Tag> tags = productModel.getTagIds()
                .stream()
                .map(item -> tagRepository.findById(item)
                        .orElseThrow(() -> new IllegalArgumentException("Not found tag with id " + item)))
                .collect(Collectors.toSet());
        product.setTags(tags);
        product.setQuantity(0);
        tags.forEach(tag -> tag.getProducts().add(product));
        return modelMapper.map(productRepository.save(product), ProductModelRes.class);
    }
    @Override
    public ProductModelRes updateProduct(Long id, UpsertProductModel productModel) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found product with id"+ id));
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
        Set<Tag> tags = productModel.getTagIds()
                .stream()
                .map(item -> tagRepository.findById(item)
                        .orElseThrow(() -> new IllegalArgumentException("Not found tag with id " + item)))
                .collect(Collectors.toSet());
        Set<Tag> existingTags = existingProduct.getTags() != null ? existingProduct.getTags() : new HashSet<>();
        tags.forEach(tag -> {
            if (!existingTags.contains(tag)) {
                existingTags.add(tag);
                tag.getProducts().add(product);
            }
        });
        product.setTags(existingTags);
        return modelMapper.map(productRepository.save(product), ProductModelRes.class);
    }

    @Override
    public ProductModelRes deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found product with id"+ id));
        product.setIsDeleted(true);
        return modelMapper.map(productRepository.save(product), ProductModelRes.class);
    }

    @Override
    public ListProductRes getProducts(ListQueryParam params) {
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
        List<ProductModelRes> productModelRes = pagedResult.getContent().stream().map(item -> modelMapper.map(item, ProductModelRes.class)).toList();
        listProductRes.setProductList(productModelRes);
        listProductRes.setTotal(pagedResult.getContent().size());

        return listProductRes;
    }
    @Override
    public ListProductRes getAllProducts() {
           List<Product> products = productRepository.findByIsDeletedIsNullOrIsDeletedFalse();
        ListProductRes listProductRes = new ListProductRes();
        List<ProductModelRes> productModelRes = products.stream().map(item -> modelMapper.map(item, ProductModelRes.class)).toList();
        listProductRes.setProductList(productModelRes);
        listProductRes.setTotal(products.size());
        return listProductRes;
    }

    @Override
    public ListProductRes getNeedInboundProducts() {
        List<Product> products = productRepository.findByIsDeletedIsNullOrIsDeletedFalse();
        ListProductRes listProductRes = new ListProductRes();
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getQuantity() < product.getMinQuantity())
                .toList();
        List<ProductModelRes> productModelRes = filteredProducts.stream()
                .map(item -> modelMapper.map(item, ProductModelRes.class))
                .toList();
        listProductRes.setProductList(productModelRes);
        listProductRes.setTotal(products.size());
        return listProductRes;
    }
}
