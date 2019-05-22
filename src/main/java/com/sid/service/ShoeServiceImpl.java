package com.sid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<Shoe> getShoes(Pageable pageable) {
		return shoeRepository.findAll(pageable);
	}

	@Override
	public List<Shoe> getShoesByBrand (String brand) {
		return shoeRepository.getShoesByBrand(brand);
	}

	@Override
	public List<Shoe> getShoesByModel (String model) {
		return shoeRepository.getShoesByModel(model);
	}

	@Override
	public Shoe addShoe(Shoe shoe) {
		return shoeRepository.save(shoe);
	}

	@Override
	public void deleteShoe(String id) {
		shoeRepository.deleteById(id);
	}


}
