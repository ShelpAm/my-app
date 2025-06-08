package com.mycompany.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Notifier
 */
public class Notifier extends VBox {

    public Notifier() {
        setMouseTransparent(true);
        // BackgroundFill backgroundFill = new BackgroundFill(Color.AQUA, new
        // CornerRadii(0),
        // new Insets(0));
        // setBackground(new Background(backgroundFill));
        setAlignment(Pos.TOP_RIGHT);
    }

    public void notifyMessage(String message) {
        Label label = new Label(message);
        label.setAlignment(Pos.CENTER_RIGHT);
        getChildren().add(label);
    }
}
