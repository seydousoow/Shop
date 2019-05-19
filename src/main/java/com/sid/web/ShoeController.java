package com.sid.web;

import com.sid.entities.Shoe;
import com.sid.service.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShoeController {

	@Autowired
	private ShoeService shoeService;

	@GetMapping(value = "/shoes")
	public Page<Shoe> getShoes(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
							   @RequestParam(value = "size", defaultValue = "28", required = false) int size,
							   @RequestParam(value = "sort", defaultValue = "addedAt", required = false) String sortBy,
							   @RequestParam(value = "direction", defaultValue = "ASC", required = false) String direction) {
		Sort sort = null;
		if( direction.isEmpty() || direction.toUpperCase().equals("ASC"))
			sort = Sort.by(Sort.Direction.ASC, sortBy);
		if (direction.toUpperCase().equals("DESC"))
			sort = Sort.by(Sort.Direction.DESC, sortBy);

		Pageable pageable = PageRequest.of(page, size, sort);

		return shoeService.getShoes(pageable);
	}

	@GetMapping(value = "/shoes/brand")
	public List<Shoe> getShoesByBrand(@RequestBody String brand){
		return shoeService.getShoesByBrand(brand);
	}
	
	@GetMapping(value = "/shoes/model")
	public List<Shoe> getShoesByModel(@RequestBody String model){
		return shoeService.getShoesByModel(model);
	}

}
