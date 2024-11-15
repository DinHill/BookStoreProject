package util;

import model.Order;

public class OrderQueue {
    private Queue<Order> queue = new Queue<>();

    // Add an order to the queue
    public void addOrder(Order order) {
        queue.enqueue(order);
    }

    // Process the next order in the queue
    public Order processOrder() {
        return queue.dequeue();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Get the size of the queue
    public int size() {
        return queue.size();
    }

    // Convert the queue to an array
    public Order[] getOrders() {
        Order[] orders = new Order[queue.size()];
        return queue.toArray(orders);
    }
}