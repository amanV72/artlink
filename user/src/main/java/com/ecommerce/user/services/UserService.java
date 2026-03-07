package com.ecommerce.user.services;



import com.ecommerce.user.dto.CreateUserRequest;
import com.ecommerce.user.dto.UpdateUserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.ArtCategory;
import com.ecommerce.user.models.Skills;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repositories.ArtCategoryRepo;
import com.ecommerce.user.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final KeyCloakAdminService adminService;
    private final ArtCategoryRepo artCategoryRepo;

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());
        response.setBio(user.getBio());
        if(user.getArtCategory()!=null){
            response.setCategory(user.getArtCategory().getName());
        }
        response.setPortfolioUrl(user.getPortfolioUrl());
        response.setProfilePicture(user.getProfilePicture());
        response.setFollowersCount(user.getFollowersCount());
        response.setFollowingCount(user.getFollowingCount());
        response.setPostsCount(user.getPostsCount());
        response.setSkills(
                user.getSkillsList() == null ? List.of() :
                        user.getSkillsList()
                                .stream()
                                .map(Skills::getSkillName)
                                .toList()
        );

        return response;

    }

    private void mapToUser(User user, CreateUserRequest userRequest) {

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFirstName()+" "+userRequest.getLastName());


    }

    private void updateUserReqToUser(User user, UpdateUserRequest userRequest) {

        if(userRequest.getFullName()!=null) user.setFullName(userRequest.getFullName());
        if(userRequest.getBio()!=null) user.setBio(userRequest.getBio());
        if(userRequest.getProfilePicture()!=null) user.setProfilePicture(userRequest.getProfilePicture());
        if(userRequest.getPortfolioUrl()!=null) user.setPortfolioUrl(userRequest.getPortfolioUrl());
        if(userRequest.getCategoryId()!=null){
            ArtCategory category= artCategoryRepo.findById(userRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
            user.setArtCategory(category);

        }
        if(userRequest.getSkillsList()!=null) {
            if(user.getSkillsList()==null){
                user.setSkillsList(new ArrayList<>());
            }else{
                user.getSkillsList().clear();
            }
            List<Skills> skills= userRequest.getSkillsList().stream()
                    .map(skillName->{
                        Skills newSkill= new Skills();
                        newSkill.setSkillName(skillName.trim().toLowerCase());
                        newSkill.setUser(user);
                        return newSkill;
                    }).toList();
            user.getSkillsList().addAll(skills);
        }


    }



    public List<UserResponse> fetchUsers() {
        List<User> users = userRepo.findAll();

        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(CreateUserRequest userRequest) {
        //user.setId(nextId++);

        String token = adminService.getAdminAccessToken();
        String keycloakUserId = adminService.createUser(token, userRequest);

        User user = new User();
        mapToUser(user, userRequest);
        user.setKeycloakId(keycloakUserId);
        adminService.assignClientRoleToUser(userRequest.getUsername(), "USER", keycloakUserId);
        userRepo.save(user);
    }

    public Optional<UserResponse> fetchUserById(String id) {
        return userRepo.findById(Long.valueOf(id)).map(this::mapToUserResponse);

    }

    public Optional<UserResponse> fetchUserByUsername(String username) {
        return userRepo.findByUsername(username).map(this::mapToUserResponse);

    }

    public boolean updateUser(String id, UpdateUserRequest userRequest) {
        return userRepo.findById(Long.valueOf(id))
                .map(existingUser -> {
                    updateUserReqToUser(existingUser, userRequest);
                    userRepo.save(existingUser);
                    return true;
                }).orElse(false);

    }


}
