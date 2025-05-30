package org.example.jobswap.Service;

import javafx.scene.control.Label;

public class WrapTextLabel extends Label {
    /**
     * A Label that has a wraptext
     * @param text the text the user wants to have in the label
     */
    public WrapTextLabel(String text) {
        super(text);
        setWrapText(true);
    }
}
