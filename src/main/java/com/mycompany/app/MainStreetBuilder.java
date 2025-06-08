package com.mycompany.app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MainStreetBuilder {
    private static final String BACKGROUND_IMAGE = "/main-street.png";

    // 按钮样式常量
    private static final double BUTTON_WIDTH = 200;
    private static final double BUTTON_HEIGHT = 80;
    private static final double BUTTON_RADIUS = 15;
    private static final Color BUTTON_COLOR = Color.rgb(255, 210, 210, 0.0); // 完全透明
    private static final Color BUTTON_HOVER_COLOR = Color.rgb(255, 180, 180, 0.0); // 半透明

    // 文本样式常量
    private static final Color TEXT_NORMAL_COLOR = Color.rgb(139, 0, 0, 0.0); // 完全透明
    private static final Color TEXT_HOVER_COLOR = Color.rgb(139, 0, 0, 1.0); // 不透明

    private static final double MARKET_X = 900, MARKET_Y = 300;
    private static final double HOSPITAL_X = 150, HOSPITAL_Y = 300;
    private static final double HOME_X = 1150, HOME_Y = 300;

    private SceneManager sceneManager;

    public MainStreetBuilder(SceneManager sm) {
        this.sceneManager = sm;
    }

    public Pane makeMainStreet() {
        Pane root = new Pane();

        // 设置背景
        Image bgImage = loadImage(BACKGROUND_IMAGE);
        ImageView background = new ImageView(bgImage);
        background.setFitWidth(sceneManager.getScene().getWidth());
        background.setFitHeight(sceneManager.getScene().getHeight());
        root.getChildren().add(background);

        // 添加场景标题
        Text title = new Text("");
        title.setFont(Font.font("Arial", 30));
        title.setFill(Color.DARKBLUE);
        title.setLayoutX(250);
        title.setLayoutY(50);
        root.getChildren().add(title);

        // 添加隐藏的按钮
        addHiddenButton(root, HOME_X + 60 - BUTTON_WIDTH / 2, HOME_Y + 120, "", SceneType.Home);
        addHiddenButton(root, MARKET_X + 60 - BUTTON_WIDTH / 2, MARKET_Y + 120, "", SceneType.PetMarket);
        addHiddenButton(root, HOSPITAL_X + 60 - BUTTON_WIDTH / 2, HOSPITAL_Y + 120, "", SceneType.Hospital);

        return root;
    }

    private void addHiddenButton(Pane root, double x, double y, String label, SceneType destSceneType) {
        // 创建按钮背景（默认透明）
        Rectangle buttonBg = new Rectangle(x, y - BUTTON_HEIGHT / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonBg.setArcWidth(BUTTON_RADIUS);
        buttonBg.setArcHeight(BUTTON_RADIUS);
        buttonBg.setFill(BUTTON_COLOR);

        // 创建按钮文本（默认透明）
        Text buttonText = new Text(label);
        buttonText.setFont(Font.font("Arial", 28));
        buttonText.setFill(TEXT_NORMAL_COLOR);
        buttonText.setTextAlignment(TextAlignment.CENTER);
        buttonText.setLayoutX(x + BUTTON_WIDTH / 2);
        buttonText.setLayoutY(y + 10); // 垂直居中调整

        // 创建可点击区域（覆盖整个按钮）
        Rectangle clickArea = new Rectangle(x, y - BUTTON_HEIGHT / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        clickArea.setFill(Color.TRANSPARENT);

        // 设置鼠标交互效果
        clickArea.setOnMouseEntered(e -> {
            buttonBg.setFill(BUTTON_HOVER_COLOR);
            buttonText.setFill(TEXT_HOVER_COLOR);
            buttonText.setStyle("-fx-font-weight: bold");
        });

        clickArea.setOnMouseExited(e -> {
            buttonBg.setFill(BUTTON_COLOR);
            buttonText.setFill(TEXT_NORMAL_COLOR);
            buttonText.setStyle("");
        });

        // 设置点击事件
        clickArea.setOnMouseClicked(e -> {
            sceneManager.enter(destSceneType);
        });

        // 添加到场景
        root.getChildren().addAll(buttonBg, buttonText, clickArea);
    }

    private static Image loadImage(String path) {
        Image image = new Image(SceneManager.class.getResourceAsStream(path));
        if (image.isError()) {
            throw new RuntimeException("图片加载失败：" + path);
        }
        return image;
    }

    public static void addAllBuildingLabels(Pane root) {
        root.getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .forEach(house -> root.getChildren().add((Text) house.getUserData()));
    }

}
