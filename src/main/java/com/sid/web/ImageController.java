package com.sid.web;

import com.sid.entities.Image;
import com.sid.service.ImageService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/images/{code}")
    @ResponseBody //Annotation to allow me send only string in response
    public Image getImage(@PathVariable String code){
        return imageService.getImage(code);
    }
}
