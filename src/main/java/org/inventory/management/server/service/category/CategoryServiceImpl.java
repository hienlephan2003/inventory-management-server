package org.inventory.management.server.service.category;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Category;
import org.inventory.management.server.model.category.CategoryRequest;
import org.inventory.management.server.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override 
    @Transactional
    public Category createCategory(CategoryRequest categoryRequest) {
        Category newCategory = Category
                .builder()
                .name(categoryRequest.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    
    @Override
    @Transactional
    public Category updateCategory(long categoryId, CategoryRequest categoryRequest) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryRequest.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    @Transactional
    public Category deleteCategory(long id)  {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException("Not found category with id"+ id));
         categoryRepository.delete(category);
        return category; 
    }
}
