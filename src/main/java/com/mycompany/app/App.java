package com.mycompany.app;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    private Market market = new Market();
    private Save currentSave;

    SceneManager sm;
    Game game = new Game();

    // Create tabs for the main interface
    // Tab marketTab = new Tab("宠物市场");
    // Tab homeTab = new Tab("宠物之家");
    // Tab hospitalTab = new Tab("宠物医院");
    // Tab achievementTab = new Tab("成就");
    // Tab exitTab = new Tab("退出");
    // VBox petsVBox = new VBox();
    // VBox bagVBox = new VBox();
    // Notifier notifier = new Notifier();

    @Override
    public void start(Stage stage) {
        stage.setTitle("宠物养成游戏");
        stage.setWidth(1408);
        stage.setHeight(792);
        stage.initStyle(StageStyle.UNDECORATED);

        sm = new SceneManager(stage);
        sm.enter(SceneType.Greet);
        stage.setScene(sm.getScene());
        stage.show();
        Game.init();
    }

    public static void exit() {
        Game.terminate();
        // TODO: save Save
        Platform.exit();
    }

    // private void yesNoBox(Pane parent, String text, Consumer<Void> yesCallback) {
    // Pane pane = new Pane();
    // pane.getStyleClass().add("message-box");
    // pane.getStyleClass().add("yesno-box");
    // parent.getChildren().add(pane);
    //
    // Label label = new Label(text);
    // label.setWrapText(true);
    // Button yesBtn = new Button("Yes");
    // yesBtn.getStyleClass().add("yes-button");
    // yesBtn.setOnAction(e -> {
    // parent.getChildren().remove(pane);
    // if (yesCallback != null) {
    // yesCallback.accept(null);
    // }
    // });
    // Button noBtn = new Button("No");
    // noBtn.getStyleClass().add("no-button");
    // noBtn.setOnAction(e -> {
    // parent.getChildren().remove(pane);
    // });
    // pane.getChildren().addAll(label, yesBtn, noBtn);
    // }
    //
    // private void inputBox(Pane parent, String text, Consumer<String> yesCallback)
    // {
    // Pane pane = new Pane();
    // pane.getStyleClass().add("message-box");
    // pane.getStyleClass().add("input-box");
    // parent.getChildren().add(pane);
    //
    // Label label = new Label(text);
    // TextField textField = new TextField();
    // Button yesBtn = new Button("Yes");
    // yesBtn.getStyleClass().add("yes-button");
    // yesBtn.setOnAction(e -> {
    // parent.getChildren().remove(pane);
    // if (yesCallback != null) {
    // yesCallback.accept(textField.getText());
    // }
    // });
    // Button noBtn = new Button("No");
    // noBtn.getStyleClass().add("no-button");
    // noBtn.setOnAction(e -> {
    // parent.getChildren().remove(pane);
    // });
    // pane.getChildren().addAll(label, textField, yesBtn, noBtn);
    // }
    //
    // private Pane makeArchiveRow(Stage stage, String name) {
    // var row = new FlowPane();
    // Button loadButton = new Button(name);
    // loadButton.getStyleClass().add("load-button");
    // loadButton.setOnAction(e -> {
    // System.out.println("TODO: load this save");
    // stage.setScene(homeScene);
    // });
    // Button removeButton = new Button();
    // removeButton.getStyleClass().add("remove-button");
    // removeButton.setOnAction(e -> {
    // yesNoBox(greetRoot, "Do you really want to delete this save FOREVER?", (y) ->
    // {
    // var parent = row.getParent();
    // if (parent instanceof Pane) {
    // ((Pane) parent).getChildren().remove(row);
    // }
    // System.out.println("TODO: remove this save"); // TODO: remove this save
    // });
    // });
    // row.getChildren().addAll(loadButton, removeButton);
    // row.getStyleClass().add("archive-row");
    // return row;
    // }
    //
    // private Scene makeGreetScene(Stage stage) {
    // Pane pane = new Pane();
    // greetRoot = pane;
    // pane.setId("pane");
    //
    // VBox itemRows = new VBox(3);
    // var saves = Save.getAllSaves();
    // for (var save : saves) {
    // itemRows.getChildren().add(makeArchiveRow(stage, save.toString()));
    // }
    // ScrollPane loadDialog = new ScrollPane(itemRows);
    // loadDialog.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    // loadDialog.setVisible(false);
    // loadDialog.setId("load-dialog");
    //
    // Button newButton = new Button();
    // newButton.getStyleClass().add("greeting-button");
    // newButton.setId("new-button");
    // newButton.setOnAction(e -> inputBox(pane, "Input your save's name:", saveName
    // -> {
    // currentSave = new Save(saveName);
    // // TODO: Play on this save
    // stage.setScene(homeScene);
    // }));
    //
    // Button loadButton = new Button();
    // loadButton.getStyleClass().add("greeting-button");
    // loadButton.setId("load-button");
    // loadButton.setOnAction(e -> loadDialog.setVisible(true));
    //
    // Button exitButton = new Button();
    // exitButton.getStyleClass().add("greeting-button");
    // exitButton.setId("exit-button");
    // exitButton.setOnAction(e -> stage.close());
    //
    // pane.getChildren().addAll(loadDialog, newButton, loadButton, exitButton);
    //
    // Scene scene = new Scene(pane);
    // scene.getStylesheets().add("styles.css");
    // Image cursorImage = new Image(getClass().getResourceAsStream("/2.png"));
    // Cursor cursor = new ImageCursor(cursorImage);
    // scene.setCursor(cursor);
    // return scene;
    // }

    // private HBox makePetBox(Pet pet) {
    // var label = new Label("您的宠物 " + pet.getName());
    // var cleanBtn = new Button("清理");
    // cleanBtn.setOnMouseClicked(e -> {
    // pet.clean();
    // });
    // var interactBtn = new Button("玩耍");
    // interactBtn.setOnMouseClicked(e -> {
    // pet.interact();
    // });
    //
    // HBox box = new HBox();
    // box.getChildren().addAll(label, cleanBtn, interactBtn);
    // return box;
    // }
    //
    // private VBox makeItemBox(Item item) {
    // var label = new Label(item.name());
    // var buyBtn = new Button(item.price() + "元");
    // buyBtn.setOnMouseClicked(e -> {
    // if (!player.canAfford(item)) {
    // notifier.notifyMessage("无法购买，金钱不足。(你当前有" + player.getMoney() + ")");
    // return;
    // }
    // player.getBag().add(item, 1);
    // player.addMoney(item.price());
    // notifier.notifyMessage("购买成功，剩余金钱: " + player.getMoney() + " (-" +
    // item.price() + ")");
    // });
    //
    // VBox box = new VBox();
    // box.setAlignment(Pos.CENTER);
    // box.getChildren().addAll(label, buyBtn);
    // return box;
    // }
    //
    // private Tab createMarketTab() {
    // // Create sub-tab pane for market
    // TabPane marketSubPane = new TabPane();
    // marketSubPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    //
    // // Create sub-tabs
    // Tab adoptionCenterTab = new Tab("领养中心");
    //
    // var adoptionRules = new ArrayList<AdoptionRequirement>();
    // adoptionRules.add(new AdoptionRequirement() {
    // @Override
    // public boolean check(Player player) {
    // return player.getName().equals("zzh");
    // }
    // });
    // PetAdoptionCenter petAdoptionCenter = PetAdoptionCenter.getInstance();
    // petAdoptionCenter.add(new JinMaoDog("金毛", 0, 100, 100, 50, 100,
    // adoptionRules));
    // petAdoptionCenter.add(new JinMaoDog("ljf", 0, 100, 100, 50, 100,
    // adoptionRules));
    // petAdoptionCenter.add(new JinMaoDog("zzh", 0, 100, 100, 50, 100,
    // adoptionRules));
    // petAdoptionCenter.add(new JinMaoDog("zyx", 0, 100, 100, 50, 100,
    // adoptionRules));
    // petAdoptionCenter.add(new JinMaoDog("DOG", 0, 100, 100, 50, 100,
    // adoptionRules));
    // petAdoptionCenter.add(new Cat("想养小猫", 0, 100, 100, 50, 100, adoptionRules));
    //
    // // Adoption Center
    // VBox vbox = new VBox();
    // vbox.getChildren().add(new Label("这里是领养中心，可以领养各种宠物"));
    // for (var pet : petAdoptionCenter.getPets()) {
    // var btn = new Button("领养 " + pet.getName());
    // vbox.getChildren().add(btn);
    // btn.setOnMouseClicked(mouseEvent -> {
    // if (!player.adopt(petAdoptionCenter, pet)) {
    // notifier.notifyMessage("无法领养 " + pet.getName() + ", 已被领养");
    // } else {
    // btn.setText(pet.getName() + " (已被领养) ");
    // // petsVBox.getChildren().add(new Label("您的宠物 " + fww.getName()));
    // petsVBox.getChildren().add(makePetBox(pet));
    // notifier.notifyMessage("您已成功领养 " + pet.getName());
    // }
    // });
    // }
    // adoptionCenterTab.setContent(vbox);
    //
    // VBox vBox2 = new VBox();
    // TilePane itemsPane = new TilePane();
    // itemsPane.setHgap(10);
    // vBox2.getChildren().addAll(new Label("这里是商场，可以购买宠物用品"), itemsPane);
    // market.addItem(new Item("狗绳", 10));
    // market.addItem(new Item("猫砂", 10));
    // market.addItem(new Item("宠物窝", 10));
    // market.addItem(new Item("鱼缸", 10));
    // market.addItem(new Item("饲养笼", 10));
    // market.addItem(new Item("宠物玩具", 114514));
    // market.addItem(new Item("家具", 114514));
    // market.addItem(new Item("清洁用品", 114514));
    // market.addItem(new Item("爱", 0));
    // market.getItems().forEach(e -> {
    // itemsPane.getChildren().add(makeItemBox(e));
    // });
    // Tab mallTab = new Tab("商场");
    // mallTab.setContent(vBox2);
    //
    // Tab bagTab = new Tab("背包", new VBox(new Label("这里是背包，含有你拥有的物品"), bagVBox));
    // player.getBag().content.addListener(new MapChangeListener<String,
    // ItemStack>() {
    // @Override
    // public void onChanged(Change<? extends String, ? extends ItemStack> change) {
    // // Refresh bagVBox
    // bagVBox.getChildren().clear();
    // for (var entry : player.getBag().content.entrySet()) {
    // ItemStack itemStack = entry.getValue();
    // String display = itemStack.getItem().name() + " *" + itemStack.getNumber();
    // bagVBox.getChildren().add(new Label(display));
    // }
    // }
    // });
    //
    // Tab returnTab = new Tab("返回");
    // returnTab.setContent(new Button("返回主界面", new javafx.scene.image.ImageView(
    // new javafx.scene.image.Image(getClass().getResourceAsStream("/1.png")))));
    // returnTab.setOnSelectionChanged(e -> {
    // if (returnTab.isSelected()) {
    // // Logic to return to main interface
    // System.out.println("Returning to main interface");
    // }
    // });
    //
    // marketSubPane.getTabs().addAll(adoptionCenterTab, mallTab, bagTab,
    // returnTab);
    // marketTab.setContent(marketSubPane);
    //
    // return marketTab;
    // }
    //
    // private Tab createHomeTab() {
    // var vbox = new VBox();
    // vbox.getChildren().add(new Label("这里是宠物之家，可以查看和管理你的宠物"));
    // vbox.getChildren().add(petsVBox);
    // homeTab.setContent(vbox);
    // return homeTab;
    // }
    //
    // private Tab createHospitalTab() {
    // hospitalTab.setContent(new Label("这里是宠物医院，可以治疗和护理宠物"));
    // return hospitalTab;
    // }
    //
    // private Tab createAchievementTab() {
    // achievementTab.setContent(new Label("这里显示你获得的成就"));
    // return achievementTab;
    // }
    //
    // private Tab createExitTab(Stage primaryStage) {
    // VBox exitContent = new VBox(20);
    // exitContent.setAlignment(Pos.CENTER);
    //
    // Label confirmLabel = new Label("确定要退出游戏吗?");
    // Button exitButton = new Button("退出游戏");
    // exitButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20;");
    // exitButton.setOnAction(e -> primaryStage.close());
    //
    // Button cancelButton = new Button("取消");
    // cancelButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20;");
    // cancelButton.setOnAction(e -> {
    // // Select the first tab instead of exit
    // exitTab.getTabPane().getSelectionModel().select(0);
    // });
    //
    // exitContent.getChildren().addAll(confirmLabel, exitButton, cancelButton);
    // exitTab.setContent(exitContent);
    //
    // return exitTab;
    // }

    public static void main(String[] args) {
        launch();
    }
}
