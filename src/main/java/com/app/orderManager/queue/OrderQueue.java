package com.app.orderManager.queue;

import org.springframework.stereotype.Component;

import com.app.orderManager.model.Order;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class OrderQueue {

    // BlockingQueue to hold orders, Tread-safe, Synchronized
    private final BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public void addOrder(Order order) {
        orderQueue.add(order);
    }

    public Order takeOrder() throws InterruptedException {
        return orderQueue.take();
    }

    public int getQueueSize() {
        return orderQueue.size();
    }
}