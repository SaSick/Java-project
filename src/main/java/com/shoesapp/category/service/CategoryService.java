package com.shoesapp.category.service;

import com.shoesapp.category.dto.CategoryDTO;
import com.shoesapp.category.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(Category category);

    List<CategoryDTO> getCategories();

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    String deleteCategory(Long categoryId);
}
