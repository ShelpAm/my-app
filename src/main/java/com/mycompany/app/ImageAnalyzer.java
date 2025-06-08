package com.mycompany.app;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

/**
 * ImageAnalyzer
 */
public class ImageAnalyzer {

    public static Point2D computeVisibleCenter(Image image) {
        PixelReader reader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        int minX = width, maxX = 0;
        int minY = height, maxY = 0;
        boolean found = false;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double alpha = reader.getColor(x, y).getOpacity();
                if (alpha > 0.05) { // Ignore very faint pixels
                    if (x < minX)
                        minX = x;
                    if (x > maxX)
                        maxX = x;
                    if (y < minY)
                        minY = y;
                    if (y > maxY)
                        maxY = y;
                    found = true;
                }
            }
        }

        if (!found)
            return new Point2D(width / 2.0, height / 2.0); // fallback
        return new Point2D((minX + maxX) / 2.0, (minY + maxY) / 2.0);
    }

}
