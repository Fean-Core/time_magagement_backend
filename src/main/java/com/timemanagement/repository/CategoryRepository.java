package com.timemanagement.repository;

import com.timemanagement.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    
    List<Category> findByUserId(String userId);
    
    List<Category> findByUserIdOrderByNameAsc(String userId);
}
