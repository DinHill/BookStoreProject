package controller;

import service.OrderService;

import java.util.Scanner;

public class OrderController {
    private OrderService orderService = new OrderService();

    // Load orders to set the correct order ID counter
    public void loadOrders() {
        orderService.loadOrders();
    }

    // Place a new order
    public void placeOrder(Scanner scanner, String customerName) {
        orderService.placeOrder(scanner, customerName);
    }

    // Process the next order in the queue
    public void processOrder() {
        orderService.processOrder();
    }

    // View all orders
    public void viewOrders() {
        orderService.viewOrders();
    }

    // View orders for a specific customer
    public void viewMyOrders(Scanner scanner, String customerName) {
        orderService.viewMyOrders(scanner, customerName);
    }

    // Search for an order by its ID
    public void searchOrder(Scanner scanner) {
        orderService.searchOrder(scanner);
    }
}