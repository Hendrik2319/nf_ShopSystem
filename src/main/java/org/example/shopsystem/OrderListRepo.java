package org.example.shopsystem;

import java.util.ArrayList;
import java.util.List;

public class OrderListRepo {

    private final List<Order> orders;

    public OrderListRepo() {
        this.orders = new ArrayList<>();
    }

    public void addOrder( Order order ) {
        orders.add(order);
    }

    public boolean removeOrder(Order order ) {
        return orders.remove(order);
    }

    public void showContent() {
        System.out.printf("OrderListRepo: [%d]%n", orders.size());
        for (Order order : orders) {
            if (order==null)
                System.out.println("    <NULL>");
            else
                order.showContent("    ");
        }
    }

    @Override
    public String toString() {
        return "OrderListRepo{" +
                "orders=" + orders +
                '}';
    }
}
