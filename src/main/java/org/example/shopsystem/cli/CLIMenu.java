package org.example.shopsystem.cli;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static org.example.shopsystem.cli.CommandLineInterface.printHR;

class CLIMenu<ReturnValue> {

    public static final TextColor COLOR_CHOICE = TextColor.TEXT_LIGHT_GREEN;
    public static final TextColor COLOR_MENU_LABEL = TextColor.TEXT_LIGHT_YELLOW;
    public static final TextColor RESET = TextColor.RESET;
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
        printHR();
        System.out.printf("Menu %s%s%s%n", COLOR_MENU_LABEL, label, RESET);

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem<ReturnValue> menuItem = menuItems.get(i);
            System.out.printf("   [%s%d%s] %s%n", COLOR_CHOICE, i+1, RESET, menuItem.label);
        }
    }

    private int askUser() {
        int choice = -1;
        while (choice <= 0 || choice > menuItems.size()) {
            System.out.printf("Enter your choice [%s1%s..%s%d%s]: ", COLOR_CHOICE, RESET, COLOR_CHOICE, menuItems.size(), RESET);
            Scanner sc = new Scanner(System.in);
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException ex) {
                choice = -1;
            }
        }
        //System.out.printf("Your choice: %s%d%s%n", COLOR_CHOICE, choice, RESET);
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
