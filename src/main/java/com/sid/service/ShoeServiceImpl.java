package com.sid.service;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
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
	
	private final ShoeRepository shoeRepository;

	public ShoeServiceImpl(ShoeRepository shoeRepository) {
		this.shoeRepository = shoeRepository;
	}

	@Override
	public Page<Shoe> getShoes(Pageable pageable) {
		return shoeRepository.findAll(pageable);
	}

	@Override
	public Shoe getShoe(String id) {
		return shoeRepository.findById(id).get();
	}

	@Override
	public List<Shoe> getShoesByBrand (String brand) {
		return shoeRepository.findByBrandEquals(brand);
	}

	@Override
	public List<Shoe> getShoesByModel (String model) {
		return shoeRepository.findByModelEquals(model);
	}

	@Override
	public Shoe addShoe(Shoe shoe) {
		String shoeCode;
		do {
			shoeCode = RandomStringUtils.randomAlphanumeric(10);
		} while(shoeRepository.findByCodeExists(shoeCode));
		shoe.setCode(shoeCode);
		return shoeRepository.save(shoe);
	}

	@Override
	public Shoe updateShoe(Shoe shoe){
		shoe.setIdShoe(shoeRepository.findByCodeEquals(shoe.getCode()).getIdShoe());
		return shoeRepository.save(shoe);
	}

	@Override
	public void deleteShoe(String id) {
		shoeRepository.deleteById(id);
	}


}
