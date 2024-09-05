package com.deepaksharma.shoppingmanagementsystem.mapper;

import com.deepaksharma.shoppingmanagementsystem.dtos.ProductDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public Product mapToProduct(ProductDTO product) {
        return Product.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .build();
    }
}
