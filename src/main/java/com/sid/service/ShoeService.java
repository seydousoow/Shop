package com.sid.service;

import com.sid.entities.Shoe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShoeService {

	Page<Shoe> getShoes(Pageable pageable);
	Shoe getShoe(String id);
	Page<Shoe> getShoesByBrands(List<String> brand, Pageable pageable);
	Shoe addShoe(Shoe shoe);
	Shoe updateShoe(Shoe shoe);
	void deleteShoe(String id);
}
