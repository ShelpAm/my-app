package com.mycompany.app;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BackpackUI {

    private final GridPane grid = new GridPane();
    private final Pane backpackPane = new Pane();

    public BackpackUI() {
        backpackPane.setPrefSize(500, 400);
        backpackPane.setStyle(
                "-fx-background-color: rgba(200,200,200,0.9); -fx-border-radius:8; -fx-background-radius:8;");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setLayoutX(20);
        grid.setLayoutY(20);
        backpackPane.getChildren().add(grid);
    }

    public Pane getView() {
        return backpackPane;
    }

    public void refresh() {
        grid.getChildren().clear();
        Bag bag = Player.getInstance().getBag();

        int cols = 5;
        int index = 0;

        for (ItemStack stack : bag.content.values()) {
            VBox cell = createCell(stack);
            int row = index / cols;
            int col = index % cols;
            grid.add(cell, col, row);
            index++;
        }
    }

    private VBox createCell(ItemStack stack) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(stack.getItem().getImagePath())));
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);

        Label name = new Label(stack.getItem().getName());
        Label quantity = new Label("×" + stack.getNumber());

        VBox vbox = new VBox(5, imageView, name, quantity);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefWidth(80);
        return vbox;
    }
}
