package com.deepaksharma.shoppingmanagementsystem.repository;

import com.deepaksharma.shoppingmanagementsystem.dtos.CategoryDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findByBrand(String brand);
    Product findByCategoryNameAndBrand(String category, String brand);
}
