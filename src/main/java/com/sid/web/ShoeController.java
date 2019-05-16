package com.sid.web;

import com.sid.entities.Shoe;
import com.sid.service.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShoeController {

	@Autowired
	private ShoeService shoeService;
	
	@GetMapping(value = "/shoes/brand")
	public List<Shoe> getShoesByBrand(@RequestBody String brand){
		return shoeService.getShoesByBrand(brand);
	}
	
	@GetMapping(value = "/shoes/model")
	public List<Shoe> getShoesByModel(@RequestBody String model){
		return shoeService.getShoesByModel(model);
	}

//	private static String encodeFileToBase64Binary(File file){
//		String encodedfile = null;
//		try {
//			FileInputStream fileInputStreamReader = new FileInputStream(file);
//			byte[] bytes = new byte[(int)file.length()];
//			fileInputStreamReader.read(bytes);
//			encodedfile = new String(Base64.encodeBase64(bytes), Charset.forName("UTF-8"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return encodedfile;
//	}
//
}
