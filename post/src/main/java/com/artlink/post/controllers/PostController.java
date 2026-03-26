package com.artlink.post.controllers;

import com.artlink.post.dtos.PostRequestDto;
import com.artlink.post.dtos.PostResponseDto;
import com.artlink.post.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> uploadPost(@RequestHeader("X-User-ID") String userId, @RequestBody PostRequestDto requestDto){
        log.info("Received request is: {}",requestDto.getCaption());
        postService.createPost(requestDto,userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> postById(@PathVariable String postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostResponseDto>> postsByUserId(@RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(postService.getPostByUserId(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> postsByTag(@RequestParam String tag){
        return ResponseEntity.ok(postService.getPostsByTag(tag));
    }

}
