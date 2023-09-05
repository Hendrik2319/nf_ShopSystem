package org.example.shopsystem;

import java.util.List;

public record Order(List<Product> products) {

    public void showContent(String indent) {
        System.out.printf("%sOrder: [%d]", indent, products.size());
        for (Product product : products) {
            if (product==null)
                System.out.printf("%s    <NULL>%n", indent);
            else
                product.showContent(indent+"    ");
        }
    }
}
