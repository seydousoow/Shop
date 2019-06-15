package com.sid.web;

import com.sid.entities.Shoe;
import com.sid.service.ShoeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

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
		return shoeService.getShoes(setPagination(page, size, sortBy, direction));
	}

	@GetMapping(value = "/shoes/{id}")
	public Shoe getShoe(@PathVariable("id") String id) { return shoeService.getShoe(id);}

	@GetMapping(value = "/shoes/brand")
	public Page<Shoe> getShoesByBrand(@RequestParam(name = "brands") String brands,
											@RequestParam(value = "page", defaultValue = "0") int page,
											@RequestParam(value = "size", defaultValue = "24") int size,
											@RequestParam(value = "sort", defaultValue = "addedAt") String sortBy,
											@RequestParam(value = "direction", defaultValue = "DESC") String direction){
		var listBrands = new AtomicReference<>(Arrays.asList(new String(Base64.getUrlDecoder().decode(brands)).split("%")));
		return shoeService.getShoesByBrands(listBrands.get(), setPagination(page, size, sortBy, direction));
	}

	@PostMapping("/shoes")
	public Shoe addShoe(@RequestBody Shoe shoe) { return shoeService.addShoe(shoe);}

	@PutMapping("/shoes")
	public Shoe updateShoe(@RequestBody Shoe shoe) { return shoeService.updateShoe(shoe);}

	@DeleteMapping("/shoes/{id}")
	public void deleteShoe(@PathVariable("id") String id) { shoeService.deleteShoe(id);}

	private Pageable setPagination(int page, int size, String sortBy, String direction){
		Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
		Sort sort = Sort.by(dir, sortBy);
		return PageRequest.of(page, size, sort);
	}

}
