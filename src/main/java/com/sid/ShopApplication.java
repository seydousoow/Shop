package com.sid;

import com.sid.repositories.ShoeRepository;
import com.sid.service.ShoeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ShoeRepository shoeRepository){
        return args -> {
            shoeRepository.findAll().forEach(shoe -> {
                shoe.setCategory("Shoe");
                shoeRepository.save(shoe);
            });
        };
    }

}
