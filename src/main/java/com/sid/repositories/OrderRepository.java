package com.sid.repositories;

import com.sid.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findByCodeClientEquals(String codeClient, Pageable pageable);

    Order findByCodeEquals(String code);

    List<Order> findByCodeClientEquals(String codeClient);
}
