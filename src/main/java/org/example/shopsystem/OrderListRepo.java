package org.example.shopsystem;

import java.util.ArrayList;
import java.util.List;

public class OrderListRepo implements OrderRepoInterface {

    private final List<Order> orders;

    public OrderListRepo() {
        this.orders = new ArrayList<>();
    }

    @Override
    public void addOrder(Order order ) {
        orders.add(order);
    }

    @Override
    public boolean removeOrder(Order order ) {
        return orders.remove(order);
    }

    @Override
    public void showContent(String indent) {
        System.out.printf("%sOrderListRepo: [%d order(s)]%n", indent, orders.size());
        for (Order order : orders) {
            if (order==null)
                System.out.printf("%s    <NULL>%n", indent);
            else
                order.showContent(indent+"    ");
        }
    }

    @Override
    public String toString() {
        return "OrderListRepo{" +
                "orders=" + orders +
                '}';
    }
}
