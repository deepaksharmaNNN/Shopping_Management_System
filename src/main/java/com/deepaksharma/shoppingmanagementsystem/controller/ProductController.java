package com.deepaksharma.shoppingmanagementsystem.controller;

import com.deepaksharma.shoppingmanagementsystem.dtos.ProductDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.FailedToSaveException;
import com.deepaksharma.shoppingmanagementsystem.response.ApiResponse;
import com.deepaksharma.shoppingmanagementsystem.service.Product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save") // http://localhost:8080/api/v1/product/save
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody @Valid ProductDTO product) {
        try {
            return ResponseEntity.ok(new ApiResponse("Product saved successfully", productService.saveProduct(product)));
        } catch (FailedToSaveException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
