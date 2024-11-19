package controller;

import model.Book;
import service.OrderService;
import util.StackADT;

import java.util.Scanner;

public class OrderController {
    private OrderService orderService;

    public OrderController(StackADT<Book> bookStack) {
        this.orderService = new OrderService(bookStack);
    }

    public void loadOrders() {
        orderService.loadOrders();
    }

    public void saveOrders() {
        orderService.saveOrders();
    }

    public void placeOrder(Scanner scanner, String customerName) {
        orderService.placeOrder(scanner, customerName);
    }

    public void viewMyOrders(Scanner scanner, String customerName) {
        orderService.viewMyOrders(scanner, customerName);
    }

    public void processOrder() {
        orderService.processOrder();
    }

    public void viewOrders() {
        orderService.viewOrders();
    }

    public void searchOrder(Scanner scanner) {
        orderService.searchOrder(scanner);
    }
}