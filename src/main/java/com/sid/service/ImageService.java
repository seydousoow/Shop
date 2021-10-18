package com.sid.service;

import com.sid.entities.Image;
import com.sid.exception.RestException;
import com.sid.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image save(Image image){
        return imageRepository.save(image);
    }

    public Image getImage(String code) {
        return imageRepository.findByCodeEquals(code);
    }

    public void updateImage(String code, String image) {
        Image img = imageRepository.findByCodeEquals(code);
        if (img == null)
            img = new Image(null, code, image);
        else
            img.setBase64Image(image);
        imageRepository.save(img);
    }

    public void deleteImage(String code) {
        Image image = imageRepository.findByCodeEquals(code);

        if(image == null || image.getBase64Image().length() <= 0)
            throw new RestException("The code " + code + " is not associated with any image");
        imageRepository.deleteByCodeEquals(code);
    }

}
