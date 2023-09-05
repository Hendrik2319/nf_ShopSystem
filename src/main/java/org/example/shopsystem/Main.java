package org.example.shopsystem;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {

        ShopService shopService = new ShopService(new OrderMapRepo());
        //ShopService shopService = new ShopService(new OrderListRepo());
        shopService.showContent();
        System.out.println();

        ProductRepo products = shopService.testInterface.products();
        OrderRepoInterface orders = shopService.testInterface.orders();

        String prodId2, prodId1, prodId3, prodId4;
        showAction("Add \"Product 1\"", products.addProduct(new Product(prodId1 = products.generateNewProductID(), "Product 1")));
        showAction("Add \"Product 2\"", products.addProduct(new Product(prodId2 = products.generateNewProductID(), "Product 2")));
        showAction("Add \"Product 3\"", products.addProduct(new Product(prodId3 = products.generateNewProductID(), "Product 3")));
        showAction("Add \"Product 4\"", products.addProduct(new Product(prodId4 = products.generateNewProductID(), "Product 4")));

        showAction("Place Order (Product 1, Product 3)", shopService.placeOrder(orders.generateNewOrderNumber(), List.of(prodId1, prodId3)));
        showAction("Place Order (2x Product 3)"        , shopService.placeOrder(orders.generateNewOrderNumber(), List.of(prodId3, prodId3)));
        showAction("Place Order (Product 1,2 & 4)"     , shopService.placeOrder(orders.generateNewOrderNumber(), List.of(prodId1, prodId2, prodId4)));
        shopService.showContent();
        System.out.println();

    }

    private static void showAction(String label, boolean success) {
        System.out.printf("%s: %s%n", label, success ? "done" : "NOT done");
    }

    public static String generateNewID(int length, boolean withDigits, boolean withLetters, Predicate<String> isInUse) {
        String id = generateNewID(length, withDigits, withLetters);
        while (isInUse.test(id))
            id = generateNewID(length, withDigits, withLetters);
        return id;
    }

    public static String generateNewID(int length, boolean withDigits, boolean withLetters) {
        if (length<=0 || (!withDigits && !withLetters))
            throw new IllegalArgumentException();

        Random rnd = new Random();
        int max = (withDigits ? 10 : 0) + (withLetters ? 26 : 0);

        StringBuilder sb = new StringBuilder();
        for (int i=0; i<length; i++) {
            int n = rnd.nextInt(max);
            if (withDigits) {
                if (n < 10)
                    sb.append((char) ('0' + n));
                else
                    sb.append((char) ('A' + (n-10)));
            } else
                sb.append((char) ('A' + n));
        }
        return sb.toString();
    }
}