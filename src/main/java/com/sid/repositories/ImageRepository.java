package com.sid.repositories;

import com.sid.entities.ItemsImages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ImageRepository extends MongoRepository<ItemsImages, String> {

    ItemsImages findByCodeEquals(@Param("code") String code);
    void deleteByCodeEquals(@Param("code") String code);
}
