package com.sid.web;

import com.sid.entities.Image;
import com.sid.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/images/{code}")
    @ResponseBody //Annotation to allow me send only string in response
    public Image getImage(@PathVariable String code){
        return imageService.getImage(code);
    }
}
