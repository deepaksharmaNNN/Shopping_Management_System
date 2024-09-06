package com.deepaksharma.shoppingmanagementsystem.service.Product;

import com.deepaksharma.shoppingmanagementsystem.dtos.ProductDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.mapper.ProductMapper;
import com.deepaksharma.shoppingmanagementsystem.model.Category;
import com.deepaksharma.shoppingmanagementsystem.model.Product;
import com.deepaksharma.shoppingmanagementsystem.repository.ProductRepository;
import com.deepaksharma.shoppingmanagementsystem.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public Product saveProduct(ProductDTO product) {
        Product newProduct = ProductMapper.mapToProduct(product);
        Category category = categoryService.findCategoryByName(product.getCategory());
        if(category == null) {
            category = categoryService.saveCategory(product.getCategory());
        }
        newProduct.setCategory(category);
        return productRepository.save(newProduct);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product findProductByName(String name) {
        Product product = productRepository.findByName(name);
        if(product == null){
            throw new ResourceNotFoundException("Product not found with name: " + name);
        }
        return product;
    }
    @Override
    public List<Product> findProductsByName(String name){
        return productRepository.findProductsByName(name);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(ProductDTO product) {
        Product existingProduct = productRepository.findByName(product.getName());
        if(existingProduct == null){
            throw new ResourceNotFoundException("Product not found with name: " + product.getName());
        }
        existingProduct.setName(product.getName());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        Category category = categoryService.findCategoryByName(product.getCategory());
        if(category == null) {
            category = categoryService.saveCategory(product.getCategory());
        }
        existingProduct.setCategory(category);
        return productRepository.save(existingProduct);
    }

    @Override
    public List<Product> findProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> findProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }
}
