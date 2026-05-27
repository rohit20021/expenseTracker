package com.project.expenseTracker.controller;

import com.project.expenseTracker.Repository.CategoryRepository;
import com.project.expenseTracker.dto.category.CategoryReq;
import com.project.expenseTracker.dto.category.CategoryResponse;
import com.project.expenseTracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/{userId}")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryReq categoryReq, @PathVariable String userId) {
        CategoryResponse res = categoryService.createCategory(categoryReq, userId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CategoryResponse>> getCategories(@PathVariable String userId) {
        List<CategoryResponse> res = categoryService.userCategories(userId);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{userId}/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryReq categoryReq,
                                                           @PathVariable Long categoryId,
                                                           @PathVariable String userId){
        CategoryResponse res = categoryService.updateCategory(categoryId, userId,categoryReq);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{userId}/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId,
                                                           @PathVariable String userId){
        categoryService.deleteCategory(categoryId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
