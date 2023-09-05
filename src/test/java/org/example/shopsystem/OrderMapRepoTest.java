package org.example.shopsystem;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapRepoTest {

    @Test
    void test_addOrder() {
        // Given
        OrderMapRepo repo = new OrderMapRepo();
        Map<String, Order> orders = repo.testInterface.orders();
        String orderNumber = "987654";
        Order order = new Order(orderNumber, Map.of(
                new Product("12345", "Name1", 100), 2,
                new Product("12346", "Name2", 120), 4,
                new Product("12347", "Name3", 140), 3,
                new Product("12348", "Name4", 160), 5
        ));

        // When
        boolean added = repo.addOrder(order);

        // Then
        assertThat(added).describedAs("return value of <addOrder>").isTrue();

        boolean orderListIsEmpty = orders.isEmpty();
        assertThat(orderListIsEmpty).describedAs("orders().isEmpty()").isFalse();

        Order order1 = orders.get(orderNumber);
        assertThat(order1).describedAs("orders().get(...)").isNotNull();
        assertThat(order1.orderNumber()).describedAs("orders().get(...).orderNumber()", orderNumber).isEqualTo(orderNumber);
    }

    @Test
    void test_getOrder() {
        // Given
        OrderMapRepo repo = new OrderMapRepo();

        String orderNumber = "987654";
        Order order = new Order(orderNumber, Map.of(
                new Product("12345", "Name1", 100), 2,
                new Product("12346", "Name2", 120), 4,
                new Product("12347", "Name3", 140), 3,
                new Product("12348", "Name4", 160), 5
        ));
        boolean wasAdded = repo.addOrder(order);

        // When
        Order returnedOrder = repo.getOrder(orderNumber);

        // Then
        assertThat(wasAdded).describedAs("return value of <addOrder>").isTrue();
        assertThat(returnedOrder).describedAs("return value of <getOrder>").isNotNull();
        assertThat(returnedOrder.orderNumber()).describedAs("orderNumber of returned order").isEqualTo(orderNumber);
    }

    @Test
    void test_removeOrder() {
        // Given
        OrderMapRepo repo = new OrderMapRepo();
        Map<String, Order> orders = repo.testInterface.orders();

        String orderNumber = "987654";
        Order order = new Order(orderNumber, Map.of(
                new Product("12345", "Name1", 100), 2,
                new Product("12346", "Name2", 120), 4,
                new Product("12347", "Name3", 140), 3,
                new Product("12348", "Name4", 160), 5
        ));
        boolean wasAdded = repo.addOrder(order);

        // When
        boolean wasRemoved = repo.removeOrder(order);

        // Then
        assertThat(wasAdded).describedAs("return value of <addOrder>").isTrue();
        assertThat(wasRemoved).describedAs("return value of <removeOrder>").isTrue();

        boolean orderListIsEmpty = orders.isEmpty();
        assertThat(orderListIsEmpty).describedAs("orders().isEmpty()").isTrue();
    }

    @Test
    void test_isUsedOrderNumber() {
        // Given
        OrderMapRepo repo = new OrderMapRepo();

        String orderNumber_inUse = "987654";
        String orderNumber_notInUse = "987655";
        Order order = new Order(orderNumber_inUse, Map.of(
                new Product("12345", "Name1", 100), 2,
                new Product("12346", "Name2", 120), 4,
                new Product("12347", "Name3", 140), 3,
                new Product("12348", "Name4", 160), 5
        ));
        boolean wasAdded = repo.addOrder(order);

        // When
        boolean isUsedOrderNumber = repo.isUsedOrderNumber(orderNumber_inUse);
        boolean isNotUsedOrderNumber = repo.isUsedOrderNumber(orderNumber_notInUse);

        // Then
        assertThat(wasAdded            ).describedAs("return value of <addOrder>").isTrue();
        assertThat(isUsedOrderNumber   ).describedAs("return value of isUsedOrderNumber(<used order number>)").isTrue();
        assertThat(isNotUsedOrderNumber).describedAs("return value of isUsedOrderNumber(<not used order number>)").isFalse();
    }

    @Test
    void test_generateNewOrderNumber() {
        // Given
        OrderMapRepo repo = new OrderMapRepo();

        String orderNumber = "987654";
        Order order = new Order(orderNumber, Map.of(
                new Product("12345", "Name1", 100), 2,
                new Product("12346", "Name2", 120), 4,
                new Product("12347", "Name3", 140), 3,
                new Product("12348", "Name4", 160), 5
        ));
        boolean wasAdded = repo.addOrder(order);

        // When
        String newOrderNumber = repo.generateNewOrderNumber();

        // Then
        assertThat(wasAdded      ).describedAs("return value of <addOrder>").isTrue();
        assertThat(newOrderNumber).describedAs("generated order number").isNotEqualTo(orderNumber);
    }
}