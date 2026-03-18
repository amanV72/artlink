package com.artlink.post.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "posts")
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private String id;

    @Indexed
    private String userId;

    private String caption;

    private List<String> tags;

    private List<String> mediaIds;

    private long likeCount;

    private long commentCount;

    @CreatedDate
    @Indexed
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
