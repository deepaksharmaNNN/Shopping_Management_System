package com.deepaksharma.shoppingmanagementsystem.service.category;

import com.deepaksharma.shoppingmanagementsystem.dtos.CategoryDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Category;

public interface CategoryService {
    Category saveCategory(CategoryDTO category);
    Category findCategoryById(Long id);
    Category findCategoryByName(String name);
    void deleteCategory(Long id);
    Category updateCategory(CategoryDTO category);
}
