package org.example.jobswap.Service;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.stage.Screen;

/**
 * UI Class to add a {@link Label} with Danfoss/Default Red color as background.
 */
public class Header extends Label {

    public Header(String text) {
        super(text);
        setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-background-color: #da291c; -fx-text-fill: white;");

        setPrefHeight(24);
        setPrefWidth(Screen.getPrimary().getBounds().getWidth());

        double padding = 12;
        setUnifiedPadding(padding);
    }

    public Header(String text, double padding) {
        super(text);
        setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-background-color: #da291c; -fx-text-fill: white;");
        setPrefHeight(12);
        setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        setUnifiedPadding(padding);
    }

    /**
     * Makes the padding is the same on all sides.
     * @param padding
     */
    public void setUnifiedPadding(double padding) {
        setPadding(new Insets(padding, padding, padding, padding));
    }
}
