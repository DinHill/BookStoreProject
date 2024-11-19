package util;

import model.Order;

public class OrderQueue {
    private QueueADT<Order> queue = new QueueADT<>();

    public void addOrder(Order order) {
        queue.enqueue(order);
    }

    public Order processOrder() {
        return queue.dequeue();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public Order get(int index) {
        return queue.get(index);
    }

    public QueueADT<Order> getAllOrders() {
        return queue;
    }
}