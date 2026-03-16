package com.artlink.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class UpdateUserRequest {
    private String fullName;
    private String bio;
    private Long categoryId;
    private String profilePicture;
    private String portfolioUrl;
    private List<String> skillsList;

}
