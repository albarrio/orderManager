package com.app.orderManager.service;

import com.app.orderManager.model.Order;
import com.app.orderManager.queue.OrderQueue;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessService {

    private final OrderQueue orderQueue = new OrderQueue();

    public void addOrder(Order order) {
        orderQueue.addOrder(order);
    }

    public Order getNextOrder() throws InterruptedException {
        return orderQueue.takeOrder();
    }

    public int getSize() {
        return orderQueue.getQueueSize();
    }
}