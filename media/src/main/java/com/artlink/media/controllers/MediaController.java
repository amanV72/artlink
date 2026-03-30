package com.artlink.media.controllers;

import com.artlink.media.dtos.MediaResponseDto;
import com.artlink.media.services.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<MediaResponseDto> uploadMedia(
            @RequestHeader("X-User-ID") String userId,
            @RequestParam("file")MultipartFile file){
        MediaResponseDto response=mediaService.uploadFile(userId,file);
        return ResponseEntity.ok(response);
    }

}
