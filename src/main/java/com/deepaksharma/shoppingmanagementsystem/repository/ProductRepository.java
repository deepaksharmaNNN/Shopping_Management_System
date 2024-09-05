package com.deepaksharma.shoppingmanagementsystem.repository;

import com.deepaksharma.shoppingmanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Product findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findProductsByName(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.brand LIKE %:brand%")
    List<Product> findByBrand(@Param("brand") String brand);

    @Query("SELECT p FROM Product p WHERE p.category.name LIKE %:category%")
    List<Product> findByCategory(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.category.name LIKE %:category% AND p.brand LIKE %:brand%")
    List<Product> findByCategoryNameAndBrand(@Param("category") String category,@Param("brand") String brand);
}
