package com.sid.web;

import com.sid.entities.Shoe;
import com.sid.service.ShoeService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

	@PostMapping("/upload")
	public void upload(@RequestParam("file") MultipartFile file) throws IOException {

		Path targetDir = Paths.get("images");

		Files.createDirectories(targetDir);//in case target directory didn't exist

		Path target = targetDir.resolve(file.getOriginalFilename());// create new path ending with `name` content
		System.out.println("copying into " + target);
		Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
		// I decided to replace already existing files with same name
	}

	private static String encodeFileToBase64Binary(File file){
		String encodedfile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int)file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = new String(Base64.encodeBase64(bytes), Charset.forName("UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encodedfile;
	}

	@GetMapping(value = "/upload")
	public File getFile(@RequestParam("name") String name) {
		File f = new File("images/" + name);
		return f;
	}
}
