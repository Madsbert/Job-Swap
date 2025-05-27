package org.example.jobswap.Controllers;

import javafx.scene.control.Tab;

public abstract class UpdatableTab extends Tab {

    public UpdatableTab() {
        super();
    }

    public UpdatableTab(String text) {
        super(text);
    }

    /**
     * Updates
     */
    public void update()
    {

    }
}
