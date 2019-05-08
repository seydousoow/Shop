package com.sid.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.sid.entities.Shoe;

@Repository
public interface ShoeRepository extends MongoRepository<Shoe, String> {
		
	@Query("{'brand' : ?0 }")
	List<Shoe> getShoesByBrand(String brand);
	
	@Query("{'model': ?0}")
	List<Shoe> getShoesByModel(String model);
}
