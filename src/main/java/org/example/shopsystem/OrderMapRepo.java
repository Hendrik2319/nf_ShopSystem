package org.example.shopsystem;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapRepo implements OrderRepoInterface {

    private final Map<String, Order> orders;

    public OrderMapRepo() {
        this.orders = new HashMap<>();
    }

    @Override
    public boolean isUsedOrderNumber(@NotNull String orderNumber) {
        return orders.containsKey(orderNumber);
    }

    @Override
    public boolean addOrder(@NotNull Order order) {
        if (isUsedOrderNumber(order.orderNumber()))
            return false;

        orders.put(order.orderNumber(), order);
        return true;
    }

    @Override
    public boolean removeOrder(@NotNull Order order) {
        Order removed = orders.remove(order.orderNumber());
        return removed!=null;
    }

    @Override
    public void showContent(@NotNull String indent) {
        System.out.printf("%sOrderMapRepo: [%d order(s)]%n", indent, orders.size());
        List<Order> orderlist = orders.keySet().stream().sorted().map(orders::get).toList();
        for (Order order : orderlist) {
            if (order==null)
                System.out.printf("%s    <NULL>%n", indent);
            else
                order.showContent(indent+"    ");
        }
    }

    @Override
    public String toString() {
        return "OrderMapRepo{" +
                "orders=" + orders +
                '}';
    }
}
