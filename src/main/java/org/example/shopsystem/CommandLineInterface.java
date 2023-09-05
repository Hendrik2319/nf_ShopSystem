package org.example.shopsystem;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class CommandLineInterface {

    private final ShopService shopService;
    private final CLIMenu mainMenu;
    private final CLIMenu productMenu;

    public CommandLineInterface(ShopService shopService) {
        this.shopService = shopService;
        this.mainMenu = new CLIMenu("Main");
        this.productMenu = new CLIMenu("Product Management");
    }

    public void start() {
        fillMainMenu();
        fillProductMenu();

        CLIMenu nextMenu = mainMenu;
        while (nextMenu!=null)
            nextMenu = nextMenu.show();
    }

    private void fillMainMenu() {
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
