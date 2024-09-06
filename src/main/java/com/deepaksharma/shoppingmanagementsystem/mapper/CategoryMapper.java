package com.deepaksharma.shoppingmanagementsystem.mapper;

import com.deepaksharma.shoppingmanagementsystem.model.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public Category mapToCategory(String category) {
        return Category.builder()
                .name(category)
                .build();
    }
}
