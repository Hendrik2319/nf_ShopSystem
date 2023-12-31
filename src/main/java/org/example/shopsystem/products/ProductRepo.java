package org.example.shopsystem.products;

import org.example.shopsystem.Displayable;
import org.example.shopsystem.Main;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ProductRepo implements Displayable {

    private final Map<String, Product> products;

    public ProductRepo() {
        this.products = new HashMap<>();
    }

    public void foreach(Consumer<Product> action) {
        products.values().forEach(action);
    }

    public String generateNewProductID() {
        return Main.generateNewID(10,true,true, products::containsKey);
    }

    public Product getProduct(@NotNull String productID ) {
        return products.get(productID);
    }

    public boolean addProduct(@NotNull Product product ) {
        if (products.containsKey(product.id()))
            return false;
        products.put(product.id(), product);
        return true;
    }

    public Product removeProduct(@NotNull String productID ) {
        return products.remove(productID);
    }

    @Override
    public void showContent(@NotNull String indent) {
        System.out.printf("%sProductRepo: [%d product(s)]%n", indent, products.size());
        List<Product> productlist = products.keySet().stream().sorted().map(products::get).toList();
        for (Product product : productlist) {
            if (product==null)
                System.out.printf("%s    <NULL>%n", indent);
            else
                product.showContent(indent+"    ");
        }
    }

    @Override
    public String toString() {
        return "ProductRepo{" +
                "products=" + products +
                '}';
    }
}
