package org.example.jobswap.Service;

import javafx.scene.layout.VBox;

public class BorderedVBox extends VBox {
    public BorderedVBox() {
        super();

        setSpacing(10);
        setStyle("-fx-border-width: 1.5; -fx-border-color: silver;");
    }
}
