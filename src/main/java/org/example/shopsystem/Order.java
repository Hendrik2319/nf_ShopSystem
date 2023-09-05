package org.example.shopsystem;

import java.util.List;

public record Order(String orderNumber, List<Product> products) implements Displayable {

    @Override
    public void showContent(String indent) {
        System.out.printf("%sOrder[%s]: [%d product(s)]", indent, orderNumber, products.size());
        for (Product product : products) {
            if (product==null)
                System.out.printf("%s    <NULL>%n", indent);
            else
                product.showContent(indent+"    ");
        }
    }
}
