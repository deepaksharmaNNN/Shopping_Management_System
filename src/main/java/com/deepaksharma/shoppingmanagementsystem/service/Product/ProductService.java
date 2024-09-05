package com.deepaksharma.shoppingmanagementsystem.service.Product;

import com.deepaksharma.shoppingmanagementsystem.dtos.ProductDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Product;

public interface ProductService {
    Product saveProduct(ProductDTO product);
    Product findProductById(Long id);
    Product findProductByName(String name);
    void deleteProduct(Long id);
    Product updateProduct(ProductDTO product);
    Product findProductByCategory(String category);
    Product findProductByBrand(String brand);
    Product findProductByCategoryAndBrand(String category, String brand);
}
