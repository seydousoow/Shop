package com.sid.repositories;

import com.sid.entities.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {

    Image findByCodeEquals(String code);

    void deleteByCodeEquals(String code);
}
