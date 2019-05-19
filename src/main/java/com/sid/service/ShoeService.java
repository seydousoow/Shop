package com.sid.service;

import java.util.List;

import com.sid.entities.Shoe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoeService {

	Page<Shoe> getShoes(Pageable pageable);
	List<Shoe> getShoesByBrand(String brand);
	List<Shoe> getShoesByModel(String model);

}
