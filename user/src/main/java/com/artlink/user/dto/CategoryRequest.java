package com.artlink.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryRequest {
    List<String> categories;
}
