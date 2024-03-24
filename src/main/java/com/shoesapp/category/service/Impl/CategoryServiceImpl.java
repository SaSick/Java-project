package com.shoesapp.category.service.impl;

import com.shoesapp.category.dto.CategoryDTO;
import com.shoesapp.category.entity.Category;
import com.shoesapp.category.repostirory.CategoryRepository;
import com.shoesapp.category.service.CategoryService;
import com.shoesapp.exception.APIException;
import com.shoesapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryDTO createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if (savedCategory != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists! ");
        }

        Category newSavedCategory = categoryRepository.save(category);

        return mapper.map(newSavedCategory, CategoryDTO.class);

    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categoryFromDB = categoryRepository.findAll();
        return categoryFromDB.stream()
                .map(category -> mapper.map(category, CategoryDTO.class)).toList();
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category categoryFromDB = findById(categoryId);
        categoryFromDB.setCategoryId(categoryId);
        categoryFromDB.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(categoryFromDB);
        return mapper.map(categoryFromDB, CategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category categoryFromDB = findById(categoryId);
        categoryRepository.delete(categoryFromDB);
        return "The category with Id " + categoryId + " is deleted!";
    }

    private Category findById(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID: " + categoryId + " not found!"));
    }
}
