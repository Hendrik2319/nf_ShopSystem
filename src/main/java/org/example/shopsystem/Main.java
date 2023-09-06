package org.example.shopsystem;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
//        showSomeActions();

        ShopService shopService = new ShopService(new OrderMapRepo());
        new CommandLineInterface(shopService).start();

//        TextColor.testValues();
    }

    @SuppressWarnings({"unused", "UnusedAssignment"})
    private static void showSomeActions() {
        ShopService shopService = new ShopService(new OrderMapRepo());
        //ShopService shopService = new ShopService(new OrderListRepo());
        shopService.showContent();
        System.out.println();

        String prodId2, prodId1, prodId3, prodId4;
        showAction("Add \"Product 1\"", shopService.addProduct(new Product(prodId1 = shopService.generateNewProductID(), "Product 1",  12_50)));
        showAction("Add \"Product 2\"", shopService.addProduct(new Product(prodId2 = shopService.generateNewProductID(), "Product 2",   3_00)));
        showAction("Add \"Product 3\"", shopService.addProduct(new Product(prodId3 = shopService.generateNewProductID(), "Product 3",     50)));
        showAction("Add \"Product 4\"", shopService.addProduct(new Product(prodId4 = shopService.generateNewProductID(), "Product 4", 500_00)));

        String orderNo1, orderNo2, orderNo3;
        showAction("[O1] Place Order (Product 1, Product 3)", shopService.placeOrder(orderNo1 = shopService.generateNewOrderNumber(), List.of(prodId1, prodId3)));
        showAction("[O2] Place Order (3x Product 3)"        , shopService.placeOrder(orderNo2 = shopService.generateNewOrderNumber(), List.of(prodId3, prodId3, prodId3)));
        showAction("[O3] Place Order (Product 1,2,4)"       , shopService.placeOrder(orderNo3 = shopService.generateNewOrderNumber(), List.of(prodId1, prodId2, prodId4)));
        shopService.showContent();
        System.out.println();

        ProductRepo products = shopService.testInterface.products();
        OrderRepoInterface orders = shopService.testInterface.orders();

        showAction("Remove 2x \"Product 3\" from order 2 [O2]", orders.getOrder(orderNo2).removeProduct(products.getProduct(prodId3), 2));
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