package org.example.shopsystem;

import org.jetbrains.annotations.NotNull;

public interface Displayable {
    default void showContent() {  showContent(""); }
    void showContent(@NotNull String indent);
    String toString();
}
