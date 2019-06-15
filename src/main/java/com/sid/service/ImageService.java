package com.sid.service;

import com.sid.entities.Image;
import com.sid.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;

    ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    protected Image save(Image image){
        return imageRepository.save(image);
    }

    public Image getImage(String code) {
        return imageRepository.findByCodeEquals(code);
    }

    protected void updateImage(String code, String image) {
        Image img = imageRepository.findByCodeEquals(code);
        img.setBase64Image(image);
        imageRepository.save(img);
    }

    protected void deleteImage(String code) {
        imageRepository.deleteByCodeEquals(code);
    }

}
