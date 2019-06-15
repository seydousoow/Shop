package com.sid.repositories;

import com.sid.entities.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ImageRepository extends MongoRepository<Image, String> {

    Image findByCodeEquals(@Param("code") String code);
    void deleteByCodeEquals(@Param("code") String code);
}
