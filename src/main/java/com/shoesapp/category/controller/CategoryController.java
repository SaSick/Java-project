package com.shoesapp.category.controller;

import com.shoesapp.category.dto.CategoryDTO;
import com.shoesapp.category.entity.Category;
import com.shoesapp.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> createCategory(
           @Valid @RequestBody Category category
    ){
        CategoryDTO savedCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        List<CategoryDTO> categories = categoryService.getCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryDTO categoryDTO
    ){
        categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable("categoryId") Long categoryId
    ){
        String status =  categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
