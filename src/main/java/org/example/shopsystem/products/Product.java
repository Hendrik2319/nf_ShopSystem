package org.example.shopsystem.products;

import org.example.shopsystem.Displayable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public record Product(@NotNull String id, @NotNull String name, int price_ct) implements Displayable {

    @Override
    public void showContent(@NotNull String indent) {
        System.out.printf(Locale.ENGLISH, "%s[%s] \"%s\" for %1.2f €%n", indent, id, name, price_ct/100.0);
    }

}
