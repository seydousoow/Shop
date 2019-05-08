package com.sid;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sid.entities.Shoe;
import com.sid.entities.Size;
import com.sid.repositories.ShoeRepository;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Autowired
    ShoeRepository shoeRepository;

    @Bean
    public CommandLineRunner start () {
        return args -> {
           
            Size size = new Size();
            size.setSize(45);
            size.setQuantity(10);
            
            Size size2 = new Size();
            size2.setSize(35);
            size2.setQuantity(100);

            Shoe shoe = new Shoe();
            shoe.setCode("1pl21i");
            shoe.setName("My sneak nike 2019");
            shoe.setAddedAt(LocalDateTime.now(ZoneId.systemDefault()));
            shoe.setBrand("Nike");
            shoe.setModel("Model");
            shoe.setBoughtPrice(199200);
            shoe.setSellingPrice(230000);
            shoe.setImageUrl("none");
            shoe.setDescription("My super extra shoes");
            shoe.setCategory("shoes");
            shoe.getListSize().addAll(Arrays.asList(size, size2,size, size2));
            shoeRepository.save(shoe);
        	
        };
    }
}
