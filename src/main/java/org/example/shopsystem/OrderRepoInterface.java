package org.example.shopsystem;

import org.jetbrains.annotations.NotNull;

public interface OrderRepoInterface extends Displayable {
    boolean addOrder(@NotNull Order order);
    boolean removeOrder(@NotNull Order order);
    boolean isUsedOrderNumber(@NotNull String orderNumber);
    default String generateNewOrderNumber() {
        return Main.generateNewID(16,true,true, this::isUsedOrderNumber);
    }
}