package org.example.shopsystem.orders;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OrderListRepo implements OrderRepoInterface {

    private final List<Order> orders;
    final TestInterface testInterface;

    public OrderListRepo() {
        this.orders = new ArrayList<>();
        this.testInterface = new TestInterface(orders);
    }

    record TestInterface(List<Order> orders) {}

    @Override
    public void foreach(Consumer<Order> action) {
        orders.forEach(action);
    }

    @Override
    public boolean isUsedOrderNumber(@NotNull String orderNumber) {
        for (Order order : orders)
            if (order!=null && orderNumber.equals(order.orderNumber()))
                return true;
        return false;
    }

    @Override
    public boolean addOrder(@NotNull Order order ) {
        if (isUsedOrderNumber(order.orderNumber()))
            return false;

        orders.add(order);
        return true;
    }

    @Override
    public Order getOrder(@NotNull String orderNumber) {
        for (Order order : orders)
            if (order!=null && orderNumber.equals(order.orderNumber()))
                return order;
        return null;
    }

    @Override
    public boolean removeOrder(@NotNull Order order ) {
        return orders.remove(order);
    }

    @Override
    public void showContent(@NotNull String indent) {
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
