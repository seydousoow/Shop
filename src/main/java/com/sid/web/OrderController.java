package com.sid.web;

import com.sid.entities.Order;
import com.sid.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Page<Order> getOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "24") int size,
                                 @RequestParam(value = "sort", defaultValue = "date") String sortBy,
                                 @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        return orderService.getOrders(page, size, sortBy, direction);
    }

    @GetMapping(value = "/client")
    public Page<Order> getOrdersByClient(@RequestParam(value = "codeClient") String codeClient,
                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "24") int size,
                                         @RequestParam(value = "sort", defaultValue = "date") String sortBy,
                                         @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        return orderService.getOrdersByClient(codeClient, page, size, sortBy, direction);
    }

    @GetMapping(value = "{code}")
    public Order getOrder(@PathVariable("code") String code) {
        return orderService.getOrder(code);
    }

    @PostMapping
    public Order saveOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
        return orderService.update(order);
    }

    @DeleteMapping(value = "{code}")
    public void deleteOrder(@PathVariable("code") String code) {
        orderService.deleteOrder(code);
    }

    @DeleteMapping
    public void deleteOrdersByClient(@RequestParam(value = "codeClient") String codeClient) {
        orderService.deleteOrdersByClient(codeClient);
    }

}
