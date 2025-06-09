package com.mycompany.app;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.concurrent.Task;
import javafx.scene.effect.Glow;

public class HospitalBuilder {
    private static int money = 1000;
    private static Label moneyLabel;
    private static ProgressBar progressBar;
    private static Label progressBarLabel;
    private static boolean isTreating = false;
    private static Button treatmentButton;
    private static ImageView petImageView;
    private static Button sceneSwitchButton; // 新增场景切换按钮
    private final SceneManager sceneManager;

    // private Button birdButton;
    // Player player;
    public HospitalBuilder(SceneManager sm) {
        this.sceneManager = sm;
    }

    public Pane makeHospital() {
        Pane root = new Pane();

        // 加载背景图片
        Image backgroundImage = new Image("hospital.jpg");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1408);
        background.setFitHeight(792);

        // 创建金钱显示标签

        moneyLabel = new Label("资金: $" + Player.getInstance().getMoney());
        moneyLabel.setFont(Font.font("Arial", 24));
        moneyLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85); " +
                "-fx-padding: 8px 15px; " +
                "-fx-border-color: #3498db; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px;");
        moneyLabel.setLayoutX(30);
        moneyLabel.setLayoutY(30);

        // 创建透明治疗按钮
        treatmentButton = new Button();
        treatmentButton.setPrefSize(230, 130);
        treatmentButton.setLayoutX(350);
        treatmentButton.setLayoutY(300);
        treatmentButton.setStyle("-fx-background-color: transparent;");

        // 创建红色进度条
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressBar.setPrefHeight(25);
        progressBar.setVisible(false);
        progressBar.setLayoutX(250);
        progressBar.setLayoutY(230);

        // 进度条标签
        progressBarLabel = new Label("");
        progressBarLabel.setFont(Font.font("Arial", 16));
        progressBarLabel.setPadding(new Insets(0, 0, 5, 0));
        progressBarLabel.setLayoutX(360);
        progressBarLabel.setLayoutY(200);
        progressBarLabel.setAlignment(Pos.CENTER);

        // 添加医院标题
        Label title = new Label("医院治疗中心");
        title.setFont(Font.font("Arial", 36));
        title.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.5, 0, 1);");
        title.setLayoutX(600);
        title.setLayoutY(80);

        // 添加提示标签
        Label hint = new Label("点击病床进行治疗 (每次治疗花费$50)");
        hint.setFont(Font.font("Arial", 18));
        hint.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold; " +
                "-fx-background-color: rgba(255,255,255,0.7); " +
                "-fx-padding: 5px; -fx-background-radius: 5px;");
        hint.setLayoutX(550);
        hint.setLayoutY(130);

        // 创建宠物图片视图
        petImageView = new ImageView();
        petImageView.setFitWidth(100);
        petImageView.setFitHeight(100);
        petImageView.setLayoutX(350);
        petImageView.setLayoutY(340);
        petImageView.setVisible(false);

        // ================== 添加切换场景按钮 ==================
        // 在makeMainStreet方法中修改切换场景按钮的创建逻辑

        sceneSwitchButton = new Button("    ");
        sceneSwitchButton.setFont(Font.font("Arial", 50));
        sceneSwitchButton.setStyle(
                "-fx-background-color: transparent; " + // 透明背景
                        "-fx-text-fill: #2c3e50; " + // 文字颜色
                        "-fx-padding: 4px 90px; " + // 内边距
                        "-fx-border-width: 0px; " + // 移除边框
                        "-fx-cursor: hand; " + // 鼠标手型
                        "-fx-effect: none;" // 移除初始阴影
        );
        sceneSwitchButton.setLayoutX(610); // 水平居中
        sceneSwitchButton.setLayoutY(650); // 底部位置
        // sceneSwitchButton.setOnAction(e -> {
        // showSceneSwitchAlert(); // 点击时显示提示对话框
        // // 这里可以添加实际场景切换逻辑（如切换到makePetMarket场景）
        // });
        sceneSwitchButton.setOnAction(e -> sceneManager.enter(SceneType.MainStreet));
        // 治疗按钮点击事件
        treatmentButton.setOnAction(e -> {
            if (!isTreating) {
                startTreatment();
            }
        });

        // 治疗按钮悬停效果
        treatmentButton.setOnMouseEntered(e -> {
            treatmentButton.setCursor(javafx.scene.Cursor.HAND);
            treatmentButton.setEffect(new Glow(0.3));
        });
        treatmentButton.setOnMouseExited(e -> {
            treatmentButton.setCursor(javafx.scene.Cursor.DEFAULT);
            treatmentButton.setEffect(null);
        });

        // 添加所有组件到根面板
        root.getChildren().addAll(
                background, moneyLabel, treatmentButton, petImageView,
                progressBar, progressBarLabel, title, hint, sceneSwitchButton);

        return root;
    }

    // // ================== 新增：切换场景提示方法 ==================
    // private static void showSceneSwitchAlert() {
    // Alert alert = new Alert(AlertType.INFORMATION);
    // alert.setTitle("场景切换");
    // alert.setHeaderText(null);
    // alert.setContentText("成功切换到新场景！");
    // alert.showAndWait();

    // }

    private static void startTreatment() {
        // 检查资金是否足够
        if (Player.getInstance().getMoney() < 50) {
            showInsufficientFunds();
            return;
        }

        isTreating = true;
        Player.getInstance().addMoney(-50); // 扣除治疗费用
        moneyLabel.setText("资金: $" + Player.getInstance().getMoney());

        // 显示宠物图片
        Image petImage = new Image(HospitalBuilder.class.getResourceAsStream("/pets/bird-1.png"));
        petImageView.setImage(petImage);
        petImageView.setVisible(true);

        // 禁用治疗按钮
        treatmentButton.setDisable(true);

        // 清除之前的绑定
        progressBar.progressProperty().unbind();
        progressBarLabel.textProperty().unbind();

        progressBar.setProgress(0);
        progressBar.setVisible(true);

        // 使用任务线程更新进度条
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("准备治疗...");
                Thread.sleep(500); // 短暂延迟

                for (int i = 1; i <= 100; i++) {
                    updateProgress(i, 100);
                    updateMessage("治疗进度: " + i + "%");
                    Thread.sleep(50); // 控制进度条更新速度
                }
                return null;
            }
        };

        // 绑定进度和消息
        progressBar.progressProperty().bind(task.progressProperty());
        progressBarLabel.textProperty().bind(task.messageProperty());

        // 任务完成后执行
        task.setOnSucceeded(e -> {
            // 解除绑定
            progressBar.progressProperty().unbind();
            progressBarLabel.textProperty().unbind();

            progressBar.setVisible(false);
            progressBarLabel.setText("");
            isTreating = false;
            treatmentButton.setDisable(false);

            // 添加完成效果
            Glow glow = new Glow(0.8);
            petImageView.setEffect(glow);

            Timeline glowTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.5), e2 -> petImageView.setEffect(null)),
                    new KeyFrame(Duration.seconds(0.5), e3 -> petImageView.setVisible(false)));
            glowTimeline.play();
        });

        // 启动任务线程
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private static void showInsufficientFunds() {
        // 创建资金不足提示
        Label insufficientFundsLabel = new Label("资金不足! 需要至少 $50");
        insufficientFundsLabel.setFont(Font.font("Arial", 18));
        insufficientFundsLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; " +
                "-fx-background-color: rgba(255,255,255,0.9); " +
                "-fx-padding: 10px; -fx-background-radius: 5px;");
        insufficientFundsLabel.setLayoutX(528);
        insufficientFundsLabel.setLayoutY(330);
        insufficientFundsLabel.setVisible(true);

        // 获取当前场景的根节点
        Pane root = (Pane) moneyLabel.getScene().getRoot();
        root.getChildren().add(insufficientFundsLabel);

        // 3秒后移除提示
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> root.getChildren().remove(insufficientFundsLabel)));
        timeline.play();
    }

    public static Scene makePetMarket() {
        Pane root = new Pane();

        Scene scene = new Scene(root);
        return scene;
    }
}