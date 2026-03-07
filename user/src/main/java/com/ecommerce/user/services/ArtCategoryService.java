package com.ecommerce.user.services;

import com.ecommerce.user.dto.CategoryRequest;
import com.ecommerce.user.dto.CategoryResponse;
import com.ecommerce.user.models.ArtCategory;
import com.ecommerce.user.repositories.ArtCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtCategoryService {
    private final ArtCategoryRepo artCategoryRepo;


    /// ADMIN USE
    public void addNewCategory(CategoryRequest categories) {

        List<ArtCategory> artCategories=categories.getCategories().stream()
                .map(cat->{
                    ArtCategory newCategory= new ArtCategory();
                    newCategory.setName(cat.trim().toLowerCase());
                    return newCategory;
        }).toList();
        artCategoryRepo.saveAll(artCategories);
    }

    public CategoryResponse getAllCategories() {
        List<String> allCategories= artCategoryRepo.findAll().stream().map(ArtCategory::getName).toList();
        CategoryResponse response= new CategoryResponse();
        response.setCategory(allCategories);
        return response;
    }
}
