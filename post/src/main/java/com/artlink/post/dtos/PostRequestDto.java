package com.artlink.post.dtos;
import lombok.Data;

import java.util.List;


@Data
public class PostRequestDto {

    private String caption;

    private List<String> tags;

    private List<MediaDto> mediaDto;

}
