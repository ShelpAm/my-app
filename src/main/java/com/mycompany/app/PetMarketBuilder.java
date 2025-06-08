package com.mycompany.app;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;

class Goods {
    String name;
    int price;
    String imageName;
    String message;

    Goods(String name, int price, String imageName) {
        this.name = name;
        this.price = price;
        this.imageName = imageName;
        this.message = "无";
    }

    Goods() {
        ;
    }
}

public class PetMarketBuilder {

    public Goods currentGood;
    private SceneManager sceneManager;

    public PetMarketBuilder(SceneManager sm) {
        this.sceneManager = sm;
    }

    private final Goods[] goods1 = {
            new Goods("111", 1, "2.png"),
            new Goods("222", 2, "1.png"),
            new Goods("333", 3, "1.png"),
    };

    private final Goods[] goods2 = {
            new Goods("222", 1, "1.png"),
            new Goods("333", 2, "2.png"),
            new Goods("444", 3, "1.png"),
    };

    private final Goods[] goods3 = {
            new Goods("333", 1, "1.png"),
            new Goods("444", 2, "2.png"),
            new Goods("555", 3, "1.png"),
    };

    private final Goods[] goods4 = {
            new Goods("666", 1, "1.png"),
            new Goods("777", 2, "1.png"),
            new Goods("888", 3, "2.png"),
    };

    private Pane makeItemRow(Pane imagePane, Label Lprice, Label Nprice, Label Lintroduce, Goods good) {
        var row = new FlowPane();
        Button rowButton = new Button(good.name);
        rowButton.setId("RowButton");
        rowButton.setOnAction(e -> {
            currentGood = good;
            imagePane.setBackground(new Background(getbgImage(currentGood.imageName)));
            Lprice.setText("金额:" + currentGood.price);
            Lintroduce.setText("商品详情:" + currentGood.message);
            Nprice.setText(currentGood.name);
        });
        row.getChildren().addAll(rowButton);
        row.setId("BuyRow");
        return row;
    }

    public BackgroundImage getbgImage(String name) {
        Image image = new Image(name);

        BackgroundImage bgImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));

        return bgImage;
    }

    public void BuyScene(Pane root, Goods[] goods) {
        Pane buyDialog = new Pane();
        buyDialog.setId("buy-dialog");
        root.getChildren().add(buyDialog);
        VBox BuyBoxRows = new VBox();
        currentGood = goods[0];
        BuyBoxRows.setBackground(new Background(getbgImage(currentGood.imageName)));

        Pane imagePane = new Pane();
        imagePane.setId("BuyImage");
        imagePane.setBackground(new Background(getbgImage(currentGood.imageName)));
        buyDialog.getChildren().add(imagePane);

        ScrollPane scrollPane = new ScrollPane(BuyBoxRows);
        scrollPane.setId("scroll-pane");
        // scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVisible(true);
        buyDialog.getChildren().add(scrollPane);

        Label Lprice = new Label();
        Lprice.setText("金额:" + currentGood.price);
        Lprice.setId("priceLabel");
        buyDialog.getChildren().add(Lprice);

        Label Nprice = new Label();
        Nprice.setText(currentGood.name);
        Nprice.setId("nameLabel");
        buyDialog.getChildren().add(Nprice);

        Label Lintroduce = new Label();
        Lintroduce.setText("商品详情:" + currentGood.message);
        Lintroduce.setId("introduceLabel");
        buyDialog.getChildren().add(Lintroduce);

        for (Goods good : goods) {
            BuyBoxRows.getChildren().add(makeItemRow(imagePane, Lprice, Nprice,
                    Lintroduce, good));
        }

        Label Lmoney = new Label();
        Lmoney.setText("余额:" + Player.getInstance().getMoney());
        Lmoney.setId("moneyLabel");
        buyDialog.getChildren().add(Lmoney);

        Button BuyBtn = new Button();
        BuyBtn.setText("购买");
        BuyBtn.setId("BuyButton");
        buyDialog.getChildren().add(BuyBtn);
        BuyBtn.setOnAction(e -> {
            Player.getInstance().addMoney(-currentGood.price);
            Lmoney.setText("余额:" + Player.getInstance().getMoney());
        });

        Button exitBtn = new Button();
        exitBtn.setText("");
        exitBtn.setId("exitButton");
        buyDialog.getChildren().add(exitBtn);
        exitBtn.setOnAction(e -> {
            buyDialog.setVisible(false);
        });
    }

    public Pane makePetMarket() {
        Pane root = new Pane();
        root.getStylesheets().add(getClass().getResource("/pet-market.css").toExternalForm());

        Button btn1 = new Button();
        btn1.setId("marketBtn1");
        root.getChildren().add(btn1);
        btn1.setOnAction(e -> {
            BuyScene(root, goods1);
        });

        Button btn2 = new Button();
        btn2.setId("marketBtn2");
        root.getChildren().add(btn2);
        btn2.setOnAction(e -> {
            BuyScene(root, goods2);
        });

        Button btn3 = new Button();
        btn3.setId("marketBtn3");
        root.getChildren().add(btn3);
        btn3.setOnAction(e -> {
            BuyScene(root, goods3);
        });

        Button btn4 = new Button();
        btn4.setId("marketBtn4");
        root.getChildren().add(btn4);
        btn4.setOnAction(e -> {
            BuyScene(root, goods4);
        });

        Button btn5 = new Button();
        btn5.setId("marketBtn5");
        root.getChildren().add(btn5);
        btn5.setOnAction(e -> {
            sceneManager.enter(SceneType.MainStreet);
        });

        return root;
    }

}
