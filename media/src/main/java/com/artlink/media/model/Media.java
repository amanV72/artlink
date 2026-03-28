package com.artlink.media.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;
    private String url;
    @Enumerated(EnumType.STRING)
    private MediaType type;
    private long size;
    private long userId;

    @CreatedDate
    private LocalDateTime createdAt;
}
