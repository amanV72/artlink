package com.ecommerce.user.dto;

import com.ecommerce.user.models.Skills;
import com.ecommerce.user.models.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String fullName;
    private String bio;
    private String category;
    private String profilePicture;
    private String portfolioUrl;
    private Integer followersCount;
    private Integer followingCount;
    private Integer postsCount;
    private List<String> skills;

}
