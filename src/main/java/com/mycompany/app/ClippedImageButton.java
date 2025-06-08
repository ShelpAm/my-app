package com.mycompany.app;

import java.net.URL;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.transform.Scale;

public class ClippedImageButton extends Button {

    public ClippedImageButton(String imagePath) {
        this(imagePath, "/s.png");
    }

    public ClippedImageButton(String imagePath, String cursorPath) {
        // super("我不想写代码了！");

        Image img = null;
        try {
            // 尝试加载图片资源
            URL imgUrl = getClass().getResource(imagePath);
            if (imgUrl == null) {
                throw new IllegalArgumentException("Image resource not found: " + imagePath);
            }
            img = new Image(imgUrl.toExternalForm());
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imagePath);
            e.printStackTrace();
            throw e;
        }

        // 创建图像视图并裁剪
        ImageView view = new ImageView(img);
        Path path = ContourClipper.buildClippingPath(img, 1);
        path.setFill(Color.BLACK);

        // 创建按钮
        this.setClip(path);
        this.setGraphic(view);
        this.setPickOnBounds(false); // 根据图像可视区域点击
        this.getStyleClass().add("clipped-button");
        if (cursorPath != null) {
            Image cursorImage = new Image(getClass().getResourceAsStream(cursorPath));
            Cursor cursor = new ImageCursor(cursorImage);
            this.setCursor(cursor);
        }

        // 悬浮动画效果
        Point2D pivot = ImageAnalyzer.computeVisibleCenter(img);
        Scale scale = new Scale(1, 1, pivot.getX(), pivot.getY());
        this.getTransforms().add(scale);
        ColorAdjust brighten = new ColorAdjust();
        brighten.setBrightness(0.1);

        this.setOnMouseEntered(e -> {
            scale.setX(1.1);
            scale.setY(1.1);
            this.setEffect(brighten);
        });
        this.setOnMouseExited(e -> {
            scale.setX(1.0);
            scale.setY(1.0);
            this.setEffect(null);
        });
    }

}
