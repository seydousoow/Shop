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

}
