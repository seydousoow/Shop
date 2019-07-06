package com.sid.repositories;

import com.sid.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findByCodeClientEquals(@Param("codeClient") String codeClient, Pageable pageable);
    Order findByCodeEquals(@Param("code") String code);
    List<Order> findByCodeClientEquals(@Param("codeClient") String codeClient);
}
