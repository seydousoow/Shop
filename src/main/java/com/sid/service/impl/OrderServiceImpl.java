package com.sid.service.impl;

import com.sid.entities.Item;
import com.sid.entities.Order;
import com.sid.exception.RestException;
import com.sid.repositories.OrderRepository;
import com.sid.service.ItemService;
import com.sid.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    private static Pageable setPageable(int page, int size, String sort, String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
        return PageRequest.of(page, size, Sort.by(dir, sort));
    }

    @Override
    public Page<Order> getOrders(int page, int size, String sort, String direction) {
        return orderRepository.findAll(setPageable(page, size, sort, direction));
    }

    @Override
    public Page<Order> getOrdersByClient(String codeClient, int page, int size, String sort, String direction) {
        val newCodeClient = new String(Base64.getDecoder().decode(codeClient));
        return orderRepository.findByCodeClientEquals(newCodeClient, setPageable(page, size, sort, direction));
    }

    @Override
    public Order getOrder(String codeOrder) {
        Order order = orderRepository.findByCodeEquals(codeOrder);
        if (order == null)
            throw new RestException("There is no order with the following code!");
        return order;
    }

    @Override
    public Order save(Order order) {
        /*
         * check if there is an order with this order's code
         */
        if (orderRepository.findByCodeEquals(order.getCode()) != null) {
            throw new RestException("There is already an order with the following code");
        }

        /*
         * update all the products of the order
         */
        order.getListItem().forEach(arg -> {
            Item item = itemService.getItem(arg.getCode());
            if (item.getSizes() != null) {
                item.getSizes().forEach(size -> {
                    if (size.getSize().equals(arg.getSize()))
                        size.setQuantity(size.getQuantity() - parseInt(arg.getQuantity()));
                });
            } else
                item.setQuantity(item.getQuantity() - parseInt(arg.getQuantity()));
            itemService.update(item);
        });
        return order;
    }

    @Override
    public Order update(Order order) {
        return save(order);
    }

    @Override
    public void deleteOrder(String code) {
        Order order = orderRepository.findByCodeEquals(code);
        if (order == null) throw new RestException("There is no order with the following code!");
        orderRepository.delete(order);
    }

    @Override
    public void deleteOrdersByClient(String codeClient) {
        List<Order> listOrder = orderRepository.findByCodeClientEquals(codeClient);
        if (listOrder.isEmpty()) throw new RestException("There is no order registered under this client");
        orderRepository.deleteAll(listOrder);
    }
}
