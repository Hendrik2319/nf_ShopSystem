package org.example.shopsystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class CLIMenu<ReturnValue> {

    private final String label;
    private final List<MenuItem<ReturnValue>> menuItems;

    CLIMenu(String label) {
        this.label = label;
        menuItems = new ArrayList<>();
    }

    void add(String label, MenuAction<ReturnValue> action) {
        menuItems.add(new MenuItem<>(label, action));
    }

    private void print() {
        System.out.printf("Menu \"%s\"%n", label);

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem<ReturnValue> menuItem = menuItems.get(i);
            System.out.printf("   [%d] %s%n", i + 1, menuItem.label);
        }
    }

    private int askUser() {
        int choice = -1;
        while (choice <= 0 || choice > menuItems.size()) {
            System.out.print("Enter your choice: ");
            Scanner sc = new Scanner(System.in);
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException ex) {
                choice = -1;
            }
        }
        System.out.printf("Your choice: %d%n", choice);
        System.out.println();

        return choice - 1; // displayed indexes are 1 higher than list indexes
    }

    ReturnValue show() {
        print();
        int choice = askUser();
        return menuItems.get(choice).action().perform();
    }

    void clear() {
        menuItems.clear();
    }

    interface MenuAction<ReturnValue> {
        ReturnValue perform();
    }

    private record MenuItem<ReturnValue>(String label, MenuAction<ReturnValue> action) {
    }
}
