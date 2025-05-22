package org.example.jobswap.Service;

import javafx.scene.layout.VBox;

/**
 * {@link VBox} with a sliver border Standard for encapturing titled areas.
 */
public class BorderedVBox extends VBox {
    public BorderedVBox() {
        super();

        setSpacing(10);
        setStyle("-fx-border-width: 1.5; -fx-border-color: silver;");
    }
}
