package com.sid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.entities.Shoe;
import com.sid.repositories.ShoeRepository;

@Service
@Transactional
public class ShoeServiceImpl implements ShoeService {
	
	@Autowired
	private ShoeRepository shoeRepository;	

	@Override
	public List<Shoe> getShoesByBrand (String brand) {
		return shoeRepository.getShoesByBrand(brand);
	}

	@Override
	public List<Shoe> getShoesByModel (String model) {
		return shoeRepository.getShoesByModel(model);
	}

	@Override
	public Shoe updateShoe (Shoe shoe) {
		return shoeRepository.save(shoe);
	}

	@Override
	public void deleteShoe (String idShoe) {
		/*
		 * Check if the shoe exists
		 */
		if (!shoeRepository.existsById(idShoe))
			throw new RuntimeException("This shoe doesn't exist into the database!");
		else
			shoeRepository.deleteById(idShoe);
	}

}
