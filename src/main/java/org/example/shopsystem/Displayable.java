package org.example.shopsystem;

import org.jetbrains.annotations.NotNull;

public interface Displayable {
    void showContent(@NotNull String indent);
    String toString();
}
