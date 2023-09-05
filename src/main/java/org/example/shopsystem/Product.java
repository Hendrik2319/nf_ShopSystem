package org.example.shopsystem;

import org.jetbrains.annotations.NotNull;

public record Product(@NotNull String id, @NotNull String name, int price_ct) implements Displayable {

    @Override
    public void showContent(@NotNull String indent) {
        System.out.printf("%s[%s] \"%s\"%n", indent, id, name);
    }

}
