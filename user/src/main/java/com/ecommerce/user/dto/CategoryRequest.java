package com.ecommerce.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryRequest {
    List<String> categories;
}
