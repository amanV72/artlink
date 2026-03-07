package com.ecommerce.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"followerId","followingId"})
)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerId;

    private Long followingId;

    @CreatedDate
    private LocalDateTime createdAt;
}
