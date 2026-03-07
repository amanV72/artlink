package com.ecommerce.user.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String keycloakId;

    @Column(nullable = false,unique = true)
    private String username;

    private String fullName;
    private String bio;
    private String profilePicture;

    @Column(nullable = false, unique = true)
    private String email;

    private String portfolioUrl;

    private Boolean isPrivateAccount=false;
    private Boolean isVerified= false;
    private Boolean isActive=true;

    private Integer followersCount=0;
    private Integer followingCount=0;
    private Integer postsCount=0;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Skills> skillsList;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ArtCategory artCategory;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
