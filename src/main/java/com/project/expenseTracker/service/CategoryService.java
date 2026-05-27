package com.project.expenseTracker.service;

import com.project.expenseTracker.Repository.CategoryRepository;
import com.project.expenseTracker.Repository.entity.Category;
import com.project.expenseTracker.dto.category.CategoryReq;
import com.project.expenseTracker.dto.category.CategoryResponse;
import com.project.expenseTracker.exceptions.DataNotFoundException;
import com.project.expenseTracker.exceptions.category.ExpCatAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryReq categoryReq, String userId) {
        // find if that category exist already by that user id;
        if(categoryRepository.findByCategoryNameAndUserId(categoryReq.getName().toLowerCase(),userId) != null){
            throw new ExpCatAlreadyExistsException("category already exist with this name","dummy errorCode");
        }
        // create new expense category
        Category newCategory = Category.builder()
                .categoryName(categoryReq.getName().toLowerCase())
                .userId(userId)
                .build();
        // save to db
        Category saveCategory = categoryRepository.save(newCategory);
        CategoryResponse response = CategoryResponse.builder()
                .name(saveCategory.getCategoryName())
                .categoryId(saveCategory.getCategoryId().toString())
                .createdAt(saveCategory.getCreatedAt().toString())
                .build();
        return response;
    }

    public List<CategoryResponse> userCategories(String userId) {
        List<Category> categories = categoryRepository.findAllByUserId(userId);
        return categories.stream()
                .map(category ->{
                    return CategoryResponse.builder()
                                .categoryId(category.getCategoryId().toString())
                                .name(category.getCategoryName())
                                .build();
                })
                .collect(Collectors.toList());
    }

    public CategoryResponse updateCategory(Long categoryId, String userId, CategoryReq categoryReq) {
        if(categoryRepository.findByCategoryNameAndUserId(categoryReq.getName(),userId) != null){
            throw new ExpCatAlreadyExistsException("category already exist with this name","dummy errorCode");
        }

        Category category = categoryRepository.findByCategoryIdAndUserId(categoryId, userId);
        category.setCategoryName(categoryReq.getName());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryResponse.builder()
                .name(updatedCategory.getCategoryName())
                .categoryId(updatedCategory.getCategoryId().toString())
                .build();
    }

    @Transactional
    public void deleteCategory(Long categoryId, String userId) {
        if(categoryRepository.findByCategoryIdAndUserId(categoryId, userId) == null){
            throw new DataNotFoundException("category with this categoryId does not exist","notFound errorCode");
        }
        categoryRepository.deleteByCategoryIdAndUserId(categoryId,userId);
    }
}
