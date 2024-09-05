package com.deepaksharma.shoppingmanagementsystem.service.Product;

import com.deepaksharma.shoppingmanagementsystem.dtos.CategoryDTO;
import com.deepaksharma.shoppingmanagementsystem.dtos.ProductDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.mapper.ProductMapper;
import com.deepaksharma.shoppingmanagementsystem.model.Category;
import com.deepaksharma.shoppingmanagementsystem.model.Product;
import com.deepaksharma.shoppingmanagementsystem.repository.ProductRepository;
import com.deepaksharma.shoppingmanagementsystem.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            category = categoryService.saveCategory(new CategoryDTO(product.getCategory()));
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
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(ProductDTO product) {
        return null;
    }

    @Override
    public Product findProductByCategory(String category) {
        Product product = productRepository.findByName(category);
        if(product == null){
            throw new ResourceNotFoundException("Product not found with category: " + category);
        }
        return product;
    }

    @Override
    public Product findProductByBrand(String brand) {
        Product product = productRepository.findByBrand(brand);
        if(product == null){
            throw new ResourceNotFoundException("Product not found with brand: " + brand);
        }
        return product;
    }

    @Override
    public Product findProductByCategoryAndBrand(String category, String brand) {
        Product product = productRepository.findByCategoryNameAndBrand(category, brand);
        if(product == null){
            throw new ResourceNotFoundException("Product not found with category: " + category + " and brand: " + brand);
        }
        return product;
    }
}
