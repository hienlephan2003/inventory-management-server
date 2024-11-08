package org.inventory.management.server.service.category;


import org.inventory.management.server.entity.Category;
import org.inventory.management.server.model.category.CategoryRequest;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest category);
    List<Category> getAll();
    Category getCategoryById(long id) ;
    Category updateCategory(long categoryId, CategoryRequest categoryRequest);

    Category deleteCategory(long id) ;
}
