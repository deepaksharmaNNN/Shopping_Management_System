package com.deepaksharma.shoppingmanagementsystem.service.category;

import com.deepaksharma.shoppingmanagementsystem.dtos.CategoryDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.mapper.CategoryMapper;
import com.deepaksharma.shoppingmanagementsystem.model.Category;
import com.deepaksharma.shoppingmanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(CategoryDTO category) {
        if(categoryRepository.findByName(category.getName()) != null){
            throw new ResourceNotFoundException("Category already exists with name: " + category.getName());
        }
        Category newCategory = CategoryMapper.mapToCategory(category.getName());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @Override
    public Category updateCategory(CategoryDTO category) {
        return null;
    }
}
