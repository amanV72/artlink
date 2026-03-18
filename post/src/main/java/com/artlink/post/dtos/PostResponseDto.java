package com.artlink.post.dtos;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {

    private String id;

    private String userId;

    private String caption;

    private List<String> tags;

    private List<String> mediaIds;

    private long likeCount;

    private long commentCount;

    private Instant createdAt;

}
