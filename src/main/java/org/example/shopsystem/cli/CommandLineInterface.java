package org.example.shopsystem.cli;

import org.example.shopsystem.orders.Order;
import org.example.shopsystem.products.Product;
import org.example.shopsystem.ShopService;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class CommandLineInterface {

    private static class SimpleMenu extends CLIMenu<SimpleMenu> {
        SimpleMenu(String label) {
            super(label);
        }
    }

    private final ShopService shopService;
    private final SimpleMenu mainMenu;
    private final SimpleMenu productMenu;
    private final SimpleMenu orderMenu;

    public CommandLineInterface(ShopService shopService) {
        this.shopService = shopService;
        this.mainMenu    = new SimpleMenu("Main");
        this.productMenu = new SimpleMenu("Product Management");
        this.orderMenu   = new SimpleMenu("Order Management");
    }

    public void start() {
        fillMainMenu();
        fillProductMenu();
        fillOrderMenu();

        SimpleMenu nextMenu = mainMenu;
        while (nextMenu!=null)
            nextMenu = nextMenu.show();
    }

    static void printHR() {
        System.out.println("-------------------------------------------------------------------");
    }

    private static String makeReturnLabel(String str) {
        return TextColor.TEXT_LIGHT_BLUE + str + TextColor.RESET;
    }

    private void fillMainMenu() {
        mainMenu.clear();

        mainMenu.add( makeReturnLabel("Quit"), ()->{
            printHR();
            System.out.println("Application stopped");
            printHR();
            return null;
        });

        mainMenu.add("Show Shop", ()->{
            printHR();
            shopService.showContent();
            System.out.println();
            return mainMenu;
        });

        mainMenu.add("Fill Shop with test values", ()->{
            printHR();

            String prodId2, prodId1, prodId3, prodId4;
            shopService.addProduct(new Product(prodId1 = shopService.generateNewProductID(), "Product 1",  12_50));
            shopService.addProduct(new Product(prodId2 = shopService.generateNewProductID(), "Product 2",   3_00));
            shopService.addProduct(new Product(prodId3 = shopService.generateNewProductID(), "Product 3",     50));
            shopService.addProduct(new Product(prodId4 = shopService.generateNewProductID(), "Product 4", 500_00));

            shopService.placeOrder(shopService.generateNewOrderNumber(), List.of(prodId1, prodId3));
            shopService.placeOrder(shopService.generateNewOrderNumber(), List.of(prodId3, prodId3, prodId3));
            shopService.placeOrder(shopService.generateNewOrderNumber(), List.of(prodId1, prodId2, prodId4));

            shopService.showContent();
            System.out.println();

            return mainMenu;
        });

        mainMenu.add("Show Terminal Colors", ()-> {
            printHR();
            TextColor.testValues();
            System.out.println();
            return mainMenu;
        });
        mainMenu.add("-> Product Management", ()->productMenu);
        mainMenu.add("-> Order Management", ()->orderMenu);
    }

    private void fillOrderMenu() {
        orderMenu.clear();

        orderMenu.add(makeReturnLabel("Return to Main Menu"), ()->mainMenu);

        orderMenu.add("Show Orders", ()-> {
            printHR();
            shopService.showOrders();
            System.out.println();
            return orderMenu;
        });

        orderMenu.add("Create new empty Order", ()->{
            printHR();
            String orderNumber = shopService.addEmptyOrder();
            System.out.printf("Created new empty order with ID \"%s\"%n", orderNumber);

            System.out.println();
            return orderMenu;
        });

        orderMenu.add("Remove order", ()->{
            Order order = selectOrder();
            if (order==null) return orderMenu;

            boolean removed = shopService.removeOrder(order);
            System.out.printf("Order \"%s\" %sremoved%n", order.orderNumber(), removed ? "" : "NOT ");
            System.out.println();

            return orderMenu;
        });

        orderMenu.add("Add product to an order", ()->{
            Order order = selectOrder();
            if (order==null) return orderMenu;

            Product product = selectProduct();
            if (product==null) return orderMenu;

            order.addProduct(product);
            System.out.printf("Product \"%s\" added to order \"%s\"%n", product.id(), order.orderNumber());
            System.out.println();

            return orderMenu;
        });

    }

    private Product selectProduct() {
        CLIMenu<Product> menu = new CLIMenu<>("Select a product");
        menu.add( makeReturnLabel("Cancel"), () -> null);
        shopService.foreachProduct(product -> menu.add("[%s] %s".formatted(product.id(), product.name()), () -> product));
        return menu.show();
    }

    private Order selectOrder() {
        CLIMenu<Order> menu = new CLIMenu<>("Select an order");
        menu.add( makeReturnLabel("Cancel"), () -> null);
        shopService.foreachOrder(order -> menu.add("[%s] %d product(s)".formatted(order.orderNumber(), order.products().size()), () -> order));
        return menu.show();
    }

    private void fillProductMenu() {
        productMenu.clear();

        productMenu.add( makeReturnLabel("Return to Main Menu"), ()->mainMenu);

        productMenu.add("Show Products", ()-> {
            printHR();
            shopService.showProducts();
            System.out.println();
            return productMenu;
        });

        productMenu.add("Add Product", ()->{
            printHR();
            System.out.println("New Product");

            String newProductID = shopService.generateNewProductID();
            System.out.printf("   ID: %s%n", newProductID);

            String name = getStringInput("   Enter a name", "q");
            if (name==null) return productMenu;

            Integer price_ct = getIntInput("   Enter a price in cent", "q", n->n>0);
            if (price_ct==null) return productMenu;

            shopService.addProduct(new Product(newProductID, name, price_ct));

            System.out.println();
            return productMenu;
        });
    }

    @SuppressWarnings("SameParameterValue")
    private static String getStringInput(String prompt, String cancelValue) {
        return getInput(prompt, cancelValue, str->true, Scanner::next);
    }

    @SuppressWarnings("SameParameterValue")
    private static Integer getIntInput(String prompt, String cancelValue, Predicate<Integer> isOk) {
        return getInput(prompt, cancelValue, isOk, sc -> {
            try { return sc.nextInt(); }
            catch (Exception ignored) {}
            return null;
        });
    }

    private static <ValueType> ValueType getInput(String prompt, String cancelValue, Predicate<ValueType> isOk, Function<Scanner,ValueType> getValueFromScanner)
    {
        ValueType choice = null;

        while (choice==null || !isOk.test(choice))
        {
            System.out.printf("%s (\"%s\" for cancel): ", prompt, cancelValue);
            Scanner sc = new Scanner(System.in);
            if (sc.hasNext(cancelValue))
                return null;

            choice = getValueFromScanner.apply(sc);
        }

        return choice;
    }

}
