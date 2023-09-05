package org.example.shopsystem;

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

    private void fillMainMenu() {
        mainMenu.clear();

        mainMenu.add("Quit", ()->{
            System.out.println("Quit application");
            return null;
        });

        mainMenu.add("Show Shop", ()->{
            shopService.showContent();
            System.out.println();
            return mainMenu;
        });

        mainMenu.add("-> Product Management", ()->productMenu);
        mainMenu.add("-> Order Management", ()->orderMenu);
    }

    private void fillOrderMenu() {
        orderMenu.clear();

        orderMenu.add("Return to Main Menu", ()->mainMenu);

        orderMenu.add("Show Orders", ()-> {
            shopService.showOrders();
            System.out.println();
            return orderMenu;
        });

        orderMenu.add("Create new empty Order", ()->{
            String orderNumber = shopService.addEmptyOrder();
            System.out.printf("Created new empty order with ID \"%s\"%n", orderNumber);

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
        menu.add("Cancel", () -> null);
        shopService.foreachProduct(product -> menu.add("[%s] %s".formatted(product.id(), product.name()), () -> product));
        return menu.show();
    }

    private Order selectOrder() {
        CLIMenu<Order> menu = new CLIMenu<>("Select an order");
        menu.add("Cancel", () -> null);
        shopService.foreachOrder(order -> menu.add("[%s] %d product(s)".formatted(order.orderNumber(), order.products().size()), () -> order));
        return menu.show();
    }

    private void fillProductMenu() {
        productMenu.clear();

        productMenu.add("Return to Main Menu", ()->mainMenu);

        productMenu.add("Show Products", ()-> {
            shopService.showProducts();
            System.out.println();
            return productMenu;
        });

        productMenu.add("Add Product", ()->{
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

    private static String getStringInput(String prompt, String cancelValue) {
        return getInput(prompt, cancelValue, str->true, Scanner::next);
    }

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
