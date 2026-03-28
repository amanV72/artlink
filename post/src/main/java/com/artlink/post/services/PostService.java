package com.artlink.post.services;

import com.artlink.post.dtos.MediaDto;
import com.artlink.post.dtos.PostRequestDto;
import com.artlink.post.dtos.PostResponseDto;
import com.artlink.post.models.Media;
import com.artlink.post.models.Post;
import com.artlink.post.repo.PostRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;

    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    @PostConstruct
    public void checkDb() {
        System.out.println("Actual DB Used: " + mongoTemplate.getDb().getName());
    }

    public Post mapToPost(PostRequestDto requestDto, String userId) {
        Post post = new Post();
        post.setUserId(userId);
        post.setCaption(requestDto.getCaption());
        post.setTags(requestDto.getTags());
        post.setMedia(requestDto.getMediaDto().stream().map(dto->{
            Media media= new Media();
            media.setId(dto.getId());
            media.setUrl(dto.getUrl());
            media.setType(dto.getType());
            return media;
        }).toList()
        );
        post.setLikeCount(0);
        post.setCommentCount(0);
        return post;
    }

    public PostResponseDto mapToPostResponse(Post post) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setUserId(post.getUserId());
        postResponseDto.setCaption(post.getCaption());
        postResponseDto.setTags(post.getTags());
        postResponseDto.setMedia(post.getMedia().stream().map(media->{
                    MediaDto dto = new MediaDto();
                    dto.setId(media.getId());
                    dto.setUrl(media.getUrl());
                    dto.setType(media.getType());
                    return dto;
        }).toList()
        );
        postResponseDto.setLikeCount(post.getLikeCount());
        postResponseDto.setCommentCount(post.getCommentCount());
        postResponseDto.setCreatedAt(post.getCreatedAt());
        return postResponseDto;
    }


    public void createPost(PostRequestDto requestDto, String userId) {
        Post post = mapToPost(requestDto, userId);
        log.info("Before saved is: {}", post.getUserId());
        Post saved = postRepo.save(post);
        log.info("After saved is: {}", saved.getUserId());
    }

    public PostResponseDto getPostById(String postId) {
        Post post = postRepo.findById(postId).orElseThrow();
        return mapToPostResponse(post);
    }

    public List<PostResponseDto> getPostByUserId(String userId) {
        List<Post> posts = postRepo.findByUserIdOrderByCreatedAtDesc(userId).orElseThrow();

        return posts
                .stream()
                .map(this::mapToPostResponse).toList();

    }

    public List<PostResponseDto> getPostsByTag(String tag) {
        List<Post> posts= postRepo.findByTagsOrderByCreatedAtDesc(tag).orElseThrow();
        return posts
                .stream()
                .map(this::mapToPostResponse).toList();
    }

    public List<PostResponseDto> getPostsForFeed() {
        List<Post> posts= postRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(0,10)).orElseThrow();
        return posts
                .stream()
                .map(this::mapToPostResponse).toList();
    }
}
