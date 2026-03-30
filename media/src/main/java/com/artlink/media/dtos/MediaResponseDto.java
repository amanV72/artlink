package com.artlink.media.dtos;

import com.artlink.media.models.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponseDto {

    private Long id;
    private String url;
    private MediaType type;
}
