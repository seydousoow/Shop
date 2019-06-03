package com.sid.service;

import java.util.List;

import com.sid.entities.Shoe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoeService {

	Page<Shoe> getShoes(Pageable pageable);
	Shoe getShoe(String id);
	List<Shoe> getShoesByBrand(String brand);
	List<Shoe> getShoesByModel(String model);
	Shoe addShoe(Shoe shoe);
	Shoe updateShoe(Shoe shoe);
	void deleteShoe(String id);

}
