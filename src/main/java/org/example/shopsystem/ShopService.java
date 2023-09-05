package org.example.shopsystem;

import java.util.ArrayList;
import java.util.List;

public class ShopService {

    private final ProductRepo products;
    private final OrderListRepo orders;

    public ShopService() {
        products = new ProductRepo();
        orders = new OrderListRepo();
    }

    public boolean placeOrder(List<String> productIDs ) {
        List<Product> products = new ArrayList<>();
        for (String productID : productIDs) {
            Product product = this.products.getProduct(productID);
            if (product==null) {
                System.err.printf("Can't add product (ID:%s) to order: Unknown product ID%n", productID);
                continue;
            }
            products.add(product);
        }
        if (products.isEmpty())
            return false;

        orders.addOrder(new Order(products));
        return true;
    }

    public void showContent(String indent) {
        System.out.printf("%sShopService:", indent);
        products.showContent(indent+"    ");
        orders.showContent(indent+"    ");
    }

    @Override
    public String toString() {
        return "ShopService{" +
                "products=" + products +
                ", orders=" + orders +
                '}';
    }
}
