package com.sid.service;

import com.sid.entities.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> getOrders(int page, int size, String sort, String direction);
    Page<Order> getOrdersByClient(String codeClient, int page, int size, String sort, String direction);
    Order getOrder(String codeOrder);

    Order save(Order order);
    Order update(Order order);
    void deleteOrder(String code);
    void deleteOrdersByClient(String codeClient);
}
