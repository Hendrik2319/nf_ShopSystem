package org.example.shopsystem;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ShopService implements Displayable {

    private final ProductRepo products;
    private final OrderRepoInterface orders;
    final TestInterface testInterface;

    public ShopService(@NotNull OrderRepoInterface orders) {
        products = new ProductRepo();
        this.orders = orders;
        this.testInterface = new TestInterface(this.products, this.orders);
    }

    public String generateNewProductID() {
        return products.generateNewProductID();
    }

    public boolean addProduct(@NotNull Product product) {
        return products.addProduct(product);
    }

    public String addEmptyOrder() {
        String newOrderNumber = orders.generateNewOrderNumber();
        orders.addOrder(new Order(newOrderNumber));
        return newOrderNumber;
    }

    public void showProducts() {
        products.showContent();
    }

    public void showOrders() {
        orders.showContent();
    }

    public void foreachProduct(Consumer<Product> action) {
        products.foreach(action);
    }

    public void foreachOrder(Consumer<Order> action) {
        orders.foreach(action);
    }

    public boolean placeOrder(@NotNull String orderNumber, @NotNull List<String> productIDs ) {
        if (orders.isUsedOrderNumber(orderNumber)) {
            System.err.printf("Can't place order (number:%s): This order number is already in use.%n", orderNumber);
            return false;
        }

        Order order = new Order(orderNumber);
        for (String productID : productIDs) {
            Product product = this.products.getProduct(productID);
            if (product==null) {
                System.err.printf("Can't add product (ID:%s) to order: Unknown product ID%n", productID);
                continue;
            }
            order.addProduct(product);
        }
        if (order.isEmpty())
            return false;

        return orders.addOrder(order);
    }

    @Override
    public void showContent(@NotNull String indent) {
        System.out.printf("%sShopService:%n", indent);
        products.showContent(indent+"    ");
        orders.showContent(indent+"    ");
    }

    @Override
    public String toString() {
        return "ShopService{" +
                "products=" + products +
                ", orders=" + orders +
                '}';
    }

    public record TestInterface(ProductRepo products, OrderRepoInterface orders) {
    }
}
