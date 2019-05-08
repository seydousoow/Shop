package com.sid.service;

import java.util.List;

import com.sid.entities.Shoe;

public interface ShoeService {
	
	List<Shoe> getShoesByBrand(String brand);
	List<Shoe> getShoesByModel(String model);
	Shoe updateShoe(Shoe shoe);
	void deleteShoe(String idShoe);
	
}
