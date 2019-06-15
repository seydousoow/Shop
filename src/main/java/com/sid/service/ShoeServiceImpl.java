package com.sid.service;

import com.sid.entities.Image;
import com.sid.entities.Shoe;
import com.sid.repositories.ShoeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShoeServiceImpl implements ShoeService {
	
	private final ShoeRepository shoeRepository;
	private final ImageService imageService;

	public ShoeServiceImpl(ShoeRepository shoeRepository,
						   ImageService imageService) {
		this.shoeRepository = shoeRepository;
		this.imageService = imageService;
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
	public Page<Shoe> getShoesByBrands(List<String> brands, Pageable pageable) {
		return shoeRepository.findByBrandIsIn(brands, pageable);
	}

	@Override
	public Shoe addShoe(Shoe shoe) {
		imageService.save(new Image(null, shoe.getCode(), shoe.getImage()));
		return shoeRepository.save(shoe);
	}

	@Override
	public Shoe updateShoe(Shoe shoe){
		if(!shoeRepository.existsById(shoe.getIdShoe()))
			throw new RuntimeException("This article doesn't exist!");

		if(shoe.getIdShoe() == null)
			shoe.setIdShoe(shoeRepository.findByCodeEquals(shoe.getCode()).getIdShoe());

		imageService.updateImage(shoe.getCode(), shoe.getImage());
		return shoeRepository.save(shoe);
	}

	@Override
	public void deleteShoe(String id) {
		if(!shoeRepository.existsById(id))
			throw new RuntimeException("This article does not exist!");

		Shoe shoe = shoeRepository.findById(id).get();
		imageService.deleteImage(shoe.getCode());
		shoeRepository.delete(shoe);
	}

}
