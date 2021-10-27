package com.sid.service;

import com.sid.entities.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Image save(MultipartFile file);

    byte[] getImage(String code, boolean scale, int width, int height);
}
