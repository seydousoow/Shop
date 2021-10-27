package com.sid.service.impl;

import com.sid.config.Utils;
import com.sid.entities.Image;
import com.sid.repositories.ImageRepository;
import com.sid.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

import static com.sid.config.Utils.decompressBytes;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;


//    public void updateImage(String code, String image) {
//        Image img = imageRepository.findByCodeEquals(code);
//        if (img == null)
//            img = new Image(null, code, image);
//        else
//            img.setBase64Image(image);
//        imageRepository.save(img);
//    }

//    public void deleteImage(String code) {
//        Image image = imageRepository.findByCodeEquals(code);
//
//        if(image == null || image.getBase64Image().length() <= 0)
//            throw new RestException("The code " + code + " is not associated with any image");
//        imageRepository.deleteByCodeEquals(code);
//    }

    @Override
    public Image save(MultipartFile file) {
//        image.setPicByte(Utils.compressBytes(image.getPicByte()));
//        return imageRepository.save(image);
        return null;
    }

    @Override
    public byte[] getImage(String name, boolean scale, int width, int height) {
        var img = imageRepository.findByName(name);
        if (Objects.isNull(img) || isBlank(img.getType())) return new byte[0];

        img.setPicByte(decompressBytes(img.getPicByte()));
        if (scale) {
            try (var bis = new ByteArrayInputStream(img.getPicByte())) {
                var is = ImageIO.read(bis);
                return Utils.resizeImage(is, img.getType().split("/")[1], width, height);
            } catch (IOException e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return img.getPicByte();
    }

}
