package com.sid.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sid.entities.Shoe;
import com.sid.service.ShoeService;

@RestController
public class ShoeController {
	
	@Autowired
	private ShoeService shoeService;
	
	@GetMapping(value = "/shoes/brand")
	public List<Shoe> getShoesByBrand(@RequestBody(required = true) String brand){
		return shoeService.getShoesByBrand(brand);
	}
	
	@GetMapping(value = "/shoes/model")
	public List<Shoe> getShoesByModel(@RequestBody(required = true) String model){
		return shoeService.getShoesByModel(model);
	}

	@PutMapping("/shoes")
	public Shoe updateShoe(@RequestBody(required = true) Shoe shoe) {
		return shoeService.updateShoe(shoe);
	}
	
	@DeleteMapping("/shoes")
	public void deleteShoe(@RequestBody(required = true) String idShoe) {
		shoeService.deleteShoe(idShoe);
	}

}
