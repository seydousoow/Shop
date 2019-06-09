package com.sid.repositories;

import com.sid.entities.Shoe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoeRepository extends MongoRepository<Shoe, String> {
	List<Shoe> findByBrandEquals(@Param("brand") String brand);
	List<Shoe> findByModelEquals(@Param("model") String model);
	Shoe findByCodeEquals(@Param("code") String code);
}
