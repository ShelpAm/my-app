package com.mycompany.app;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.*;

public class PetMarketBuilder {

    public Item currentGood;
    private SceneManager sceneManager;

    public PetMarketBuilder(SceneManager sm) {
        this.sceneManager = sm;
    }

    private final List<Item> goods1 = getAllDefaultGoods0();
    private final List<Item> goods2 = getAllDefaultGoods1();

    public static List<Item> getAllDefaultGoods0() {
        List<Item> all = new ArrayList<>();
        all.addAll(MustObject.getDefaultMustObjects());
        all.addAll(Pet.getDefaultPets());
        return all;
    }

    public static List<Item> getAllDefaultGoods1() {
        List<Item> all = new ArrayList<>();
        all.addAll(Toy.getDefaultToys());
        all.addAll(Food.getDefaultFoods());
        return all;
    }

    private Pane makeItemRow(Pane imagePane, Label Lprice, Label Nprice, Label Lintroduce, Item good) {
        var row = new FlowPane();
        Button rowButton = new Button(good.getName());
        rowButton.setId("RowButton");
        rowButton.setOnAction(e -> {
            currentGood = good;
            imagePane.setBackground(new Background(getbgImage(currentGood.getImagePath())));
            Lprice.setText("金额:" + currentGood.getPrice());
            Lintroduce.setText("商品详情:" + currentGood.getDescription());
            Nprice.setText(currentGood.getName());
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

    public void BuyScene(Pane root, List<Item> goods) {
        Pane buyDialog = new Pane();
        buyDialog.setId("buy-dialog");
        root.getChildren().add(buyDialog);
        VBox BuyBoxRows = new VBox();
        currentGood = goods.get(0);

        Pane imagePane = new Pane();
        imagePane.setId("BuyImage");
        imagePane.setBackground(new Background(getbgImage(currentGood.getImagePath())));
        buyDialog.getChildren().add(imagePane);
        imagePane.setBackground(new Background(getbgImage(currentGood.getImagePath())));

        ScrollPane scrollPane = new ScrollPane(BuyBoxRows);
        scrollPane.setId("scroll-pane");
        // scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVisible(true);
        buyDialog.getChildren().add(scrollPane);

        Label Lprice = new Label();
        Lprice.setText("金额:" + currentGood.getPrice());
        Lprice.setId("priceLabel");
        buyDialog.getChildren().add(Lprice);

        Label Nprice = new Label();
        Nprice.setText(currentGood.getName());
        Nprice.setId("nameLabel");
        buyDialog.getChildren().add(Nprice);

        Label Lintroduce = new Label();
        Lintroduce.setText("商品详情:" + currentGood.getDescription());
        Lintroduce.setId("introduceLabel");
        Lintroduce.setWrapText(true);
        buyDialog.getChildren().add(Lintroduce);

        for (Item good : goods) {
            // System.out.println("便利" + good);
            BuyBoxRows.getChildren().add(makeItemRow(imagePane, Lprice, Nprice,
                    Lintroduce, good));
        }

        Label Lmoney = new Label();
        Lmoney.setText("余额:" + Player.getInstance().getMoney());
        Lmoney.setId("moneyLabel");
        buyDialog.getChildren().add(Lmoney);

        Button BuyBtn = new Button();
        // BuyBtn.setText("购买");
        BuyBtn.setId("BuyButton");
        buyDialog.getChildren().add(BuyBtn);

        var label = new MessageLabel(buyDialog);

        BuyBtn.setOnAction(e -> {
            if (Player.getInstance().getMoney() >= currentGood.getPrice()) {
                if (currentGood.getIsFood() == 0
                        && Player.getInstance().getBag().content.containsKey(currentGood.getName())) {
                    label.showMessage("该商品只能购买一个", 3);
                } else {
                    Player.getInstance().addMoney(-currentGood.getPrice());
                    Lmoney.setText("余额:" + Player.getInstance().getMoney());
                    Player.getInstance().getBag().add(currentGood, 1);

                    UIBuilder.backpackUI.refresh();
                    if (currentGood instanceof Pet) {
                        Home.getInstance().getPets().add((Pet) currentGood);
                    }
                    Home.getInstance().refreshAll();
                }
            } else {
                label.showMessage("余额不足，无法购买", 3);
            }
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
        root.setId("market-pane");

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

        // Button btn3 = new Button();
        // btn3.setId("marketBtn3");
        // root.getChildren().add(btn3);
        // btn3.setOnAction(e -> {
        // BuyScene(root, goods3);
        // });
        //
        // Button btn4 = new Button();
        // btn4.setId("marketBtn4");
        // root.getChildren().add(btn4);
        // btn4.setOnAction(e -> {
        // BuyScene(root, goods4);
        // });

        Button btn5 = new Button();
        btn5.setId("marketBtn5");
        root.getChildren().add(btn5);
        btn5.setOnAction(e -> {
            sceneManager.enter(SceneType.MainStreet);
        });

        return root;
    }

}
