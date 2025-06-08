package com.mycompany.app;

import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

enum SceneType {
    Greet,
    Home,
    MainStreet,
    PetMarket,
    Hospital,
    TODO,
}

public class SceneManager {

    private final Stage stage;
    private final Scene scene;

    private final StackPane layers;
    private final Pane root;
    private final Pane ui;
    private final Pane prompt;

    private final Pane greet;
    private final Pane home;
    private final Pane mainStreet;
    private final Pane petMarket;
    private final Pane hospital;

    public SceneManager(Stage stage) {
        this.stage = stage;
        this.layers = new StackPane();
        this.scene = new Scene(layers, stage.getWidth(), stage.getHeight());
        scene.getStylesheets().add(getClass().getResource("/general.css").toExternalForm());
        Image cursorImage = new Image(getClass().getResourceAsStream("/zhuazi.png"));
        Cursor cursor = new ImageCursor(cursorImage);
        scene.setCursor(cursor);
        this.stage.setScene(scene);

        this.root = new Pane();
        this.root.setId("root");

        this.ui = makeUI();
        ui.setPickOnBounds(false);

        this.prompt = new Pane();
        this.prompt.setId("prompt");
        this.prompt.setVisible(false);

        layers.getChildren().addAll(this.root, this.ui, this.prompt);

        greet = makeGreet();
        home = makeHome();
        mainStreet = makeMainStreet();
        petMarket = makePetMarket();
        hospital = makeHospital();
        setAllInvisible();
        root.getChildren().addAll(greet, home, mainStreet, petMarket, hospital);
    }

    public Scene getScene() {
        return scene;
    }

    public void enter(SceneType sceneType) {
        setAllInvisible();

        switch (sceneType) {
            case Greet:
                greet.setVisible(true);
                ui.setVisible(false);
                break;
            case Home:
                home.setVisible(true);
                ui.setVisible(true);
                break;
            case MainStreet:
                mainStreet.setVisible(true);
                ui.setVisible(true);
                break;
            case PetMarket:
                petMarket.setVisible(true);
                ui.setVisible(true);
                break;
            case Hospital:
                hospital.setVisible(true);
                ui.setVisible(true);
                break;
            case TODO:
                throw new RuntimeException();
        }
    }

    private void setAllInvisible() {
        greet.setVisible(false);
        home.setVisible(false);
        mainStreet.setVisible(false);
        petMarket.setVisible(false);
        hospital.setVisible(false);
    }

    private Pane makeArchiveRow(Stage stage, String name) {
        var row = new FlowPane();
        Button loadButton = new Button(name);
        loadButton.getStyleClass().add("load-button");
        loadButton.setOnAction(e -> {
            System.out.println("TODO: load this save");
            enter(SceneType.Home);
        });
        Button removeButton = new Button();
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> {
            yesNoBox("Do you really want to delete this save FOREVER?", (y) -> {
                var parent = row.getParent();
                if (parent instanceof Pane) {
                    ((Pane) parent).getChildren().remove(row);
                }
                System.out.println("TODO: remove this save"); // TODO: remove this save
            });
        });
        row.getChildren().addAll(loadButton, removeButton);
        row.getStyleClass().add("archive-row");
        return row;
    }

    private void yesNoBox(String text, Consumer<Void> yesCallback) {
        Pane pane = new Pane();
        pane.getStyleClass().add("message-box");
        pane.getStyleClass().add("yesno-box");
        prompt.getChildren().add(pane);

        Label label = new Label(text);
        label.setWrapText(true);
        Button yesBtn = new Button("Yes");
        yesBtn.getStyleClass().add("yes-button");
        yesBtn.setOnAction(e -> {
            prompt.getChildren().remove(pane);
            prompt.setVisible(false);
            if (yesCallback != null) {
                yesCallback.accept(null);
            }
        });
        Button noBtn = new Button("No");
        noBtn.getStyleClass().add("no-button");
        noBtn.setOnAction(e -> {
            prompt.getChildren().remove(pane);
            prompt.setVisible(false);
        });
        pane.getChildren().addAll(label, yesBtn, noBtn);

        prompt.setVisible(true);
    }

    private void inputBox(String text, Consumer<String> yesCallback) {
        Pane pane = new Pane();
        pane.getStyleClass().add("message-box");
        pane.getStyleClass().add("input-box");
        prompt.getChildren().add(pane);

        Label label = new Label(text);
        TextField textField = new TextField();
        Button yesBtn = new Button("Yes");
        yesBtn.getStyleClass().add("yes-button");
        yesBtn.setOnAction(e -> {
            prompt.getChildren().remove(pane);
            prompt.setVisible(false);
            if (yesCallback != null) {
                yesCallback.accept(textField.getText());
            }
        });
        Button noBtn = new Button("No");
        noBtn.getStyleClass().add("no-button");
        noBtn.setOnAction(e -> {
            prompt.getChildren().remove(pane);
            prompt.setVisible(false);
        });
        pane.getChildren().addAll(label, textField, yesBtn, noBtn);

        prompt.setVisible(true);
    }

    private Pane makeGreet() {
        Pane root = new Pane();
        root.getStylesheets().add(
                getClass().getResource("/greet.css").toExternalForm());

        root.setPrefSize(scene.getWidth(), scene.getHeight());
        root.setId("greet");
        VBox itemRows = new VBox(3);
        var saves = Save.getAllSaves();
        for (var save : saves) {
            itemRows.getChildren().add(makeArchiveRow(stage, save.toString()));
        }
        ScrollPane loadDialog = new ScrollPane(itemRows);
        loadDialog.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        loadDialog.setVisible(false);
        loadDialog.setId("load-dialog");

        Button newButton = new Button();
        newButton.getStyleClass().add("greeting-button");
        newButton.setId("new-button");
        newButton.setOnAction(e -> {
            inputBox("Input your save's name:", saveName -> {
                // currentSave = new Save(saveName);
                // TODO: Play on this save
                enter(SceneType.Home);
            });
        });

        Button loadButton = new Button();
        loadButton.getStyleClass().add("greeting-button");
        loadButton.setId("load-button");
        loadButton.setOnAction(e -> loadDialog.setVisible(!loadDialog.isVisible()));

        Button exitButton = new Button();
        exitButton.getStyleClass().add("greeting-button");
        exitButton.setId("exit-button");
        exitButton.setOnAction(e -> {
            stage.close();
            Platform.exit();
        });

        root.getChildren().addAll(loadDialog, newButton, loadButton, exitButton);

        return root;
    }

    private Pane makeMainStreet() {
        var mainStreetBuilder = new MainStreetBuilder(this);
        return mainStreetBuilder.makeMainStreet();
    }

    private Pane makePetMarket() {
        var PetMarketBuilder = new PetMarketBuilder(this);
        return PetMarketBuilder.makePetMarket();
    }

    private Pane makeHospital() {
        return HospitalBuilder.makeHospital();
    }

    private Pane makeHome() {
        return new HomeBuilder(this).makeHome();
    }

    private Pane makeUI() {
        return new UIBuilder(this).makeUI();
    }

}
