package org.example.shopsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepo {

    private final Map<String, Product> products;

    public ProductRepo() {
        this.products = new HashMap<>();
    }

    public Product getProduct( String productID ) {
        return products.get(productID);
    }

    public boolean addProduct( Product product ) {
        if (products.containsKey(product.id()))
            return false;
        products.put(product.id(), product);
        return true;
    }

    public Product removeProduct(String productID ) {
        return products.remove(productID);
    }

    public void showContent() {
        System.out.printf("ProductRepo: [%d]%n", products.size());
        List<Product> productlist = products.keySet().stream().sorted().map(products::get).toList();
        for (Product product : productlist) {
            if (product==null)
                System.out.println("    <NULL>");
            else
                product.showContent("    ");
        }
    }

    @Override
    public String toString() {
        return "ProductRepo{" +
                "products=" + products +
                '}';
    }
}
