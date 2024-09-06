package com.deepaksharma.shoppingmanagementsystem.controller;

import com.deepaksharma.shoppingmanagementsystem.dtos.ProductDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.FailedToSaveException;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.response.ApiResponse;
import com.deepaksharma.shoppingmanagementsystem.service.Product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.version}/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    // save product
    @PostMapping("/save") // http://localhost:8080/api/v1/product/save
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody @Valid ProductDTO product) {
        try {
            return ResponseEntity.ok(new ApiResponse("Product saved successfully", productService.saveProduct(product)));
        } catch (FailedToSaveException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // find product by Name
    @GetMapping("/find-by/name/{name}") // http://localhost:8080/api/v1/product/find-by/name/{name}
    public ResponseEntity<ApiResponse> findProductByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(new ApiResponse("Product found successfully", productService.findProductsByName(name)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // find product by id
    @GetMapping("/find-by/id/{id}") // http://localhost:8080/api/v1/product/find-by/id/{id}
    public ResponseEntity<ApiResponse> findProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new ApiResponse("Product found successfully", productService.findProductById(id)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // find product by category
    @GetMapping("/find-by/category/{category}") // http://localhost:8080/api/v1/product/find-by/category/{category}
    public ResponseEntity<ApiResponse> findProductsByCategory(@PathVariable String category) {
        try {
            return ResponseEntity.ok(new ApiResponse("Product found successfully", productService.findProductsByCategory(category)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // find product by brand
    @GetMapping("/find-by/brand/{brand}") // http://localhost:8080/api/v1/product/find-by/brand/{brand}
    public ResponseEntity<ApiResponse> findProductsByBrand(@PathVariable String brand) {
        try {
            return ResponseEntity.ok(new ApiResponse("Product found successfully", productService.findProductsByBrand(brand)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // find product by category and brand
    @GetMapping("/find-by/category-brand")
    public ResponseEntity<ApiResponse> findProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            return ResponseEntity.ok(new ApiResponse("Product found successfully", productService.findProductsByCategoryAndBrand(category, brand)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // get all products
    @GetMapping("/all") // http://localhost:8080/api/v1/product/all
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            return ResponseEntity.ok(new ApiResponse("All products", productService.findAllProducts()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}