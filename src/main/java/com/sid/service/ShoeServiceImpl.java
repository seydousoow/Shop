package com.sid.service;

import java.util.List;
import java.util.Random;

import com.sid.entities.ItemsImages;
import com.sid.repositories.ImageRepository;
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
	private final ImageRepository imageRepository;

	public ShoeServiceImpl(ShoeRepository shoeRepository, ImageRepository imageRepository) {
		this.shoeRepository = shoeRepository;
		this.imageRepository = imageRepository;
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
		} while(shoeRepository.findByCodeEquals(shoeCode) != null);
		shoe.setCode(shoeCode);

		imageRepository.save(new ItemsImages(null, shoe.getCode(), shoe.getImage()));

		return shoeRepository.save(shoe);
	}

	@Override
	public Shoe updateShoe(Shoe shoe){
		if(!shoeRepository.existsById(shoe.getIdShoe()))
			throw new RuntimeException("This article doesn't exist!");

		if(shoe.getIdShoe() == null)
			shoe.setIdShoe(shoeRepository.findByCodeEquals(shoe.getCode()).getIdShoe());

		updateImage(shoe.getCode(), shoe.getImage());
		return shoeRepository.save(shoe);
	}

	@Override
	public void deleteShoe(String id) {
		if(!shoeRepository.existsById(id))
			throw new RuntimeException("This article does not exist!");

		Shoe shoe = shoeRepository.findById(id).get();
		deleteImage(shoe.getCode());
		shoeRepository.delete(shoe);
	}

	@Override
	public ItemsImages getImage(String code) {
		return imageRepository.findByCodeEquals(code);
	}

	private void updateImage(String code, String image) {
		ItemsImages img = imageRepository.findByCodeEquals(code);
		img.setBase64Image(image);
		imageRepository.save(img);
	}

	private void deleteImage(String code) {
		imageRepository.deleteByCodeEquals(code);
	}


}
