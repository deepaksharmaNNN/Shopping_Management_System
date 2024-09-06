package com.deepaksharma.shoppingmanagementsystem.controller;

import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.response.ApiResponse;
import com.deepaksharma.shoppingmanagementsystem.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/category")
public class CategoryController {
    private final CategoryService categoryService;

    //get all categories
    @GetMapping("/all") //http://localhost:8080/api/v1/category/all
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            return ResponseEntity.ok(new ApiResponse("Categories fetched successfully", categoryService.getAllCategories()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //get category by id
    @GetMapping("/id/{id}") //http://localhost:8080/api/v1/category/id/1
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(new ApiResponse("Category fetched successfully", categoryService.findCategoryById(id)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //get category by name
    @GetMapping("/name/{name}") //http://localhost:8080/api/v1/category/name/Books
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            return ResponseEntity.ok(new ApiResponse("Category fetched successfully", categoryService.findCategoryByName(name)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //delete category by id
    @GetMapping("/delete/{id}") //http://localhost:8080/api/v1/category/delete/1
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id){
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //update category
    @GetMapping("/update") //http://localhost:8080/api/v1/category/update
    public ResponseEntity<ApiResponse> updateCategory(@RequestParam Long id, @RequestParam String name){
        try {
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", categoryService.updateCategory(id, name)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //add category
    @PostMapping("/add") //http://localhost:8080/api/v1/category/add
    public ResponseEntity<ApiResponse> addCategory(@RequestParam String name){
        try {
            return ResponseEntity.ok(new ApiResponse("Category added successfully", categoryService.saveCategory(name)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
