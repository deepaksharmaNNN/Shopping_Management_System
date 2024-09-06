package com.deepaksharma.shoppingmanagementsystem.service.category;

import com.deepaksharma.shoppingmanagementsystem.model.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(String name);
    Category findCategoryById(Long id);
    Category findCategoryByName(String name);
    void deleteCategory(Long id);
    Category updateCategory(Long id, String name);
    List<Category> getAllCategories();
}
