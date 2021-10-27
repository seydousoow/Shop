package com.sid.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;

@Getter
@Setter
public class ImageResponseDto {
    private String name;
    private String type;
    private InputStreamResource resource;
}
