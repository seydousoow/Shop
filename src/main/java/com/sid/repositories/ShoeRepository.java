package com.sid.repositories;

import com.sid.entities.Shoe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ShoeRepository extends MongoRepository<Shoe, String> {
	Page<Shoe> findByBrandIsIn(@Param("brands") List<String> brands, Pageable pageable);
	Shoe findByCodeEquals(@Param("code") String code);
}
