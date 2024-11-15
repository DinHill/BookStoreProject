package service;

import model.Order;

public class SearchingService {
    // Perform binary search on an array of orders
    public int binarySearch(Order[] orders, int id) {
        int low = 0;
        int high = orders.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (orders[mid].getId() == id) {
                return mid;
            }
            if (orders[mid].getId() < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}