package com.shoesapp.category.repostirory;

import com.shoesapp.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where lower(c.categoryName)  = lower(:categoryName)")
    Category findByCategoryName(String categoryName);
}
