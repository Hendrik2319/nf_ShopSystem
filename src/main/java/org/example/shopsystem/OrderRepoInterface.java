package org.example.shopsystem;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface OrderRepoInterface extends Displayable {
    void    foreach(Consumer<Order> action);
    boolean addOrder(@NotNull Order order);
    Order   getOrder(@NotNull String orderNumber);
    boolean removeOrder(@NotNull Order order);
    boolean isUsedOrderNumber(@NotNull String orderNumber);
    default String generateNewOrderNumber() {
        return Main.generateNewID(16,true,true, this::isUsedOrderNumber);
    }
}