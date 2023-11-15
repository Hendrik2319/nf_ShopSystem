package org.example.shopsystem.orders;

import org.example.shopsystem.Displayable;
import org.example.shopsystem.products.Product;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public record Order(@NotNull String orderNumber, @NotNull Map<Product,Integer> products) implements Displayable {

    public Order(@NotNull String orderNumber) {
        this(orderNumber, new HashMap<>());
    }

    public void addProduct(Product product) {
        Integer n = products.get(product);
        products.put(product, n==null ? 1 : n+1);
    }

    public boolean removeProduct(Product product, int amount) {
        if (amount<0) {
            Integer n = products.remove(product);
            return n!=null;
        }

        Integer n = products.get(product);
        if (n==null) return false;

        n = Math.max(0, n-amount);
        if (n==0)
            products.remove(product);
        else
            products.put(product, n);

        return true;
    }

    public int getTotalPrice() {
        int sum_ct = 0;
        for (Product product : products.keySet()) {
            int n = products.get(product);
            sum_ct += product.price_ct() * n;
        }
        return sum_ct;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    @Override
    public void showContent(@NotNull String indent) {
        System.out.printf("%sOrder[%s]: [%d product(s)]%n", indent, orderNumber, products.size());
        System.out.printf(Locale.ENGLISH, "%s    Total Price: %1.2f €%n", indent, getTotalPrice()/100.0);
        List<Product> productlist = products.keySet().stream().sorted(Comparator.comparing(Product::id)).toList();
        for (Product product : productlist) {
            Integer n = products.get(product);
            product.showContent("%s    %dx ".formatted(indent, n));
        }
    }
}
