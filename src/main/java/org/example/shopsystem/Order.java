package org.example.shopsystem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Order(@NotNull String orderNumber, @NotNull List<Product> products) implements Displayable {

    @Override
    public void showContent(@NotNull String indent) {
        System.out.printf("%sOrder[%s]: [%d product(s)]%n", indent, orderNumber, products.size());
        for (Product product : products) {
            if (product==null)
                System.out.printf("%s    <NULL>%n", indent);
            else
                product.showContent(indent+"    ");
        }
    }
}
