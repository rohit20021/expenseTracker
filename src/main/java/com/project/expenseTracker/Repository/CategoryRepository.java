package com.project.expenseTracker.Repository;

import com.project.expenseTracker.Repository.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);

    Category findByCategoryNameAndUserId(String name, String userId);

    List<Category> findAllByUserId(String userId);

    Category findByCategoryIdAndUserId(Long categoryId, String userId);

    void deleteByCategoryIdAndUserId(Long categoryId, String userId);
}