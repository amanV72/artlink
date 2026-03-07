package com.ecommerce.user.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "skill_categories")
@Data
public class ArtCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}