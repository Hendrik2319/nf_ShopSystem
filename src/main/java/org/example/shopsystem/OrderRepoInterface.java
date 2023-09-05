package org.example.shopsystem;

public interface OrderRepoInterface extends Displayable {
    void addOrder(Order order);
    boolean removeOrder(Order order);
}