package com.app.orderManager.controller;

import com.app.orderManager.model.Order;
import com.app.orderManager.model.OrderStatus;
import com.app.orderManager.service.OrderProcessService;
import com.app.orderManager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    OrderProcessService orderProcessService = new OrderProcessService();

    @PostMapping("/order/create")
    public String createOrderPost(@RequestBody Order order) {
        Order orderCreated = orderService.createOrder(order);
        orderCreated.setCodeFromId();
        orderProcessService.addOrder(orderCreated);
        return orderCreated.getId().toString();
    }

    @PostMapping("/order/status")
    public String getStatusOrder(@RequestBody Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        return order.getStatus();
    }

    @PostMapping("/order/complete")
    public String setOrderComplete(@RequestBody Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        order.setStatus(OrderStatus.COMPLETED.name());
        orderService.updateOrder(order);
        return order.getStatus();
    }

    @GetMapping("/order/process")
    public String processOrder() {
        Order order = null;
        try {
            order = orderProcessService.getNextOrder();
            order.setStatus(OrderStatus.IN_PROGRESS.name());
            orderService.updateOrder(order);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return order.getId().toString();
    }

    @GetMapping("/order/size")
    public String processOrderSize() {
        return String.valueOf(orderProcessService.getSize());
    }
}
