package com.artlink.media.services;

import com.artlink.media.dtos.MediaResponseDto;
import com.artlink.media.models.Media;
import com.artlink.media.models.MediaType;
import com.artlink.media.repo.MediaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MediaRepo mediaRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public MediaResponseDto uploadFile(String userId, MultipartFile file) {

        //generate unique filename
        String fileName= UUID.randomUUID() + "_" + file.getOriginalFilename();

        MediaType type=getMediaType(file.getContentType());

        String folder;

        switch(type){
            case IMAGE -> folder="image";
            case VIDEO -> folder="video";
            case AUDIO -> folder="audio";
            case DOCUMENT -> folder="document";
            default -> folder="other";
        }
        Path path= Paths.get(uploadDir,folder,fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path,file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String url= "http://localhost:8083/media/"+ folder +"/"+ fileName;

        Media media= new Media();
        media.setUrl(url);
        media.setFileName(fileName);
        media.setType(type);
        media.setUserId(userId);
        media.setSize(file.getSize());

        Media newMedia=mediaRepo.save(media);

        return new MediaResponseDto(newMedia.getId(), url,type);

    }

    public MediaType getMediaType(String contentType){
        if(contentType==null) return MediaType.OTHER;
        if(contentType.startsWith("image")) return MediaType.IMAGE;
        if(contentType.startsWith("video")) return MediaType.VIDEO;
        if(contentType.startsWith("audio")) return MediaType.AUDIO;
        return MediaType.DOCUMENT;
    }
}
