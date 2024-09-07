package com.deepaksharma.shoppingmanagementsystem.service.Product;

import com.deepaksharma.shoppingmanagementsystem.dtos.ProductDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(ProductDTO product);
    Product findProductById(Long id);
    Product findProductByName(String name);
    List<Product> findProductsByName(String name);
    void deleteProduct(Long id);
    List<Product> findAllProducts();
    Product updateProduct(ProductDTO product);
    List<Product> findProductsByCategory(String category);
    List<Product> findProductsByBrand(String brand);
    List<Product> findProductsByCategoryAndBrand(String category, String brand);
    Product save(Product product);
}
