package com.artlink.user.controllers;

import com.artlink.user.dto.CategoryRequest;
import com.artlink.user.dto.CategoryResponse;
import com.artlink.user.services.ArtCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final ArtCategoryService artCategoryService;

    @PostMapping("/admin/categories")
    public ResponseEntity<String> newCategory(@RequestBody CategoryRequest categories){
        artCategoryService.addNewCategory(categories);
        return ResponseEntity.ok("Categories added successfully");
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponse> getCategories(){
        return ResponseEntity.ok(artCategoryService.getAllCategories());
    }
}
