package org.example.shopsystem;

public record Product(String id, String name) {

    public void showContent(String indent) {
        System.out.printf("%s[%s] \"%s\"%n", indent, id, name);
    }

}
