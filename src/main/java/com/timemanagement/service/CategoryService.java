package com.timemanagement.service;

import com.timemanagement.dto.category.CreateCategoryRequest;
import com.timemanagement.dto.category.UpdateCategoryRequest;
import com.timemanagement.exception.ResourceNotFoundException;
import com.timemanagement.model.Category;
import com.timemanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Category> findByUserId(String userId) {
        return categoryRepository.findByUserIdOrderByNameAsc(userId);
    }
    
    public Category findByIdAndUserId(String id, String userId) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        if (!category.get().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        return category.get();
    }
    
    public Category createCategory(CreateCategoryRequest request, String userId) {
        Category category = new Category();
        category.setName(request.getName());
        category.setColor(request.getColor());
        category.setIcon(request.getIcon());
        category.setUserId(userId);
        return categoryRepository.save(category);
    }
    
    public Category updateCategory(String id, UpdateCategoryRequest request, String userId) {
        Category existingCategory = findByIdAndUserId(id, userId);
        
        if (request.getName() != null) {
            existingCategory.setName(request.getName());
        }
        if (request.getColor() != null) {
            existingCategory.setColor(request.getColor());
        }
        if (request.getIcon() != null) {
            existingCategory.setIcon(request.getIcon());
        }
        
        return categoryRepository.save(existingCategory);
    }
    
    public void deleteCategory(String id, String userId) {
        Category category = findByIdAndUserId(id, userId);
        categoryRepository.delete(category);
    }
}
