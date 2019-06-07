package com.sid.web;

import com.sid.entities.ItemsImages;
import com.sid.entities.Shoe;
import com.sid.service.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class ShoeController {

	private final ShoeService shoeService;

	public ShoeController(ShoeService shoeService) {
		this.shoeService = shoeService;
	}

	@GetMapping(value = "/shoes")
	public Page<Shoe> getShoes(@RequestParam(value = "page", defaultValue = "0") int page,
							   @RequestParam(value = "size", defaultValue = "24") int size,
							   @RequestParam(value = "sort", defaultValue = "addedAt") String sortBy,
							   @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
		Sort sort = Sort.by(dir, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		return shoeService.getShoes(pageable);
	}

	@GetMapping(value = "/shoes/{id}")
	public Shoe getShoe(@PathVariable("id") String id) { return shoeService.getShoe(id);}

	@GetMapping(value = "/shoes/brand")
	public Collection<Shoe> getShoesByBrand(@RequestBody List<String> brands){
		Collection<Shoe> shoes = new ArrayList<>();
		brands.forEach(brand -> shoes.addAll(shoeService.getShoesByBrand(brand)));
		return shoes;
	}
	
	@GetMapping(value = "/shoes/model")
	public Collection<Shoe> getShoesByModel(@RequestBody List<String> models){
		Collection<Shoe> shoes = new ArrayList<>();
		models.forEach(model -> shoes.addAll(shoeService.getShoesByModel(model)));
		return shoes;
	}

	@GetMapping(value = "/shoes/image/{code}")
	public ItemsImages getImages(@PathVariable String code) {
		return shoeService.getImage(code);
	}

	@PostMapping("/shoes")
	public Shoe addShoe(@RequestBody Shoe shoe) { return shoeService.addShoe(shoe);}

	@PutMapping("/shoes")
	public Shoe updateShoe(@RequestBody Shoe shoe) { return shoeService.updateShoe(shoe);}

	@DeleteMapping("/shoes/{id}")
	public void deleteShoe(@PathVariable("id") String id) { shoeService.deleteShoe(id);}

}
