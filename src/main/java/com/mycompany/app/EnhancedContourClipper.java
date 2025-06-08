package com.mycompany.app;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnhancedContourClipper {
    /**
     * 1) Marching Squares 提取次像素轮廓
     * 2) Catmull-Rom 样条平滑
     * 3) Douglas-Peucker 简化
     */
    public static Path buildClippingPath(Image img, double threshold, double smoothness, double epsilon) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();
        PixelReader pr = img.getPixelReader();
        // 读取 alpha 通道
        float[][] alpha = new float[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int a = (pr.getArgb(x, y) >> 24) & 0xFF;
                alpha[y][x] = a / 255f;
            }
        }
        // 提取初始轮廓点
        List<Point2D> raw = marchingSquares(alpha, threshold);
        if (raw.isEmpty())
            return new Path();
        // 平滑曲线
        List<Point2D> smooth = catmullRomSpline(raw, smoothness);
        // 简化
        List<Point2D> simple = douglasPeucker(smooth, epsilon);
        // 构建 Path
        Path path = new Path();
        Point2D first = simple.get(0);
        path.getElements().add(new MoveTo(first.getX(), first.getY()));
        for (int i = 1; i < simple.size(); i++) {
            Point2D p = simple.get(i);
            path.getElements().add(new LineTo(p.getX(), p.getY()));
        }
        path.getElements().add(new ClosePath());
        return path;
    }

    // 基于 Marching Squares 的轮廓提取
    private static List<Point2D> marchingSquares(float[][] alpha, double t) {
        int h = alpha.length;
        int w = alpha[0].length;
        List<Point2D> contour = new ArrayList<>();
        // 边缘插值函数
        for (int y = 0; y < h - 1; y++) {
            for (int x = 0; x < w - 1; x++) {
                int state = 0;
                if (alpha[y][x] > t)
                    state |= 1;
                if (alpha[y][x + 1] > t)
                    state |= 2;
                if (alpha[y + 1][x] > t)
                    state |= 4;
                if (alpha[y + 1][x + 1] > t)
                    state |= 8;
                if (state == 0 || state == 15)
                    continue;
                // 对每条相交边进行插值
                // 上边
                if ((state & 1) != (state & 2)) {
                    double v1 = alpha[y][x], v2 = alpha[y][x + 1];
                    double t1 = (t - v1) / (v2 - v1);
                    contour.add(new Point2D(x + t1, y));
                }
                // 右边
                if ((state & 2) != (state & 8)) {
                    double v1 = alpha[y][x + 1], v2 = alpha[y + 1][x + 1];
                    double t1 = (t - v1) / (v2 - v1);
                    contour.add(new Point2D(x + 1, y + t1));
                }
                // 下边
                if ((state & 4) != (state & 8)) {
                    double v1 = alpha[y + 1][x], v2 = alpha[y + 1][x + 1];
                    double t1 = (t - v1) / (v2 - v1);
                    contour.add(new Point2D(x + t1, y + 1));
                }
                // 左边
                if ((state & 1) != (state & 4)) {
                    double v1 = alpha[y][x], v2 = alpha[y + 1][x];
                    double t1 = (t - v1) / (v2 - v1);
                    contour.add(new Point2D(x, y + t1));
                }
            }
        }
        return contour;
    }

    // Catmull-Rom 插值，smoothness: 每个段插入点数
    private static List<Point2D> catmullRomSpline(List<Point2D> pts, double smoothness) {
        int n = pts.size();
        List<Point2D> out = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point2D p0 = pts.get((i - 1 + n) % n);
            Point2D p1 = pts.get(i);
            Point2D p2 = pts.get((i + 1) % n);
            Point2D p3 = pts.get((i + 2) % n);
            out.add(p1);
            for (int k = 1; k <= smoothness; k++) {
                double t = k / (smoothness + 1);
                double t2 = t * t, t3 = t2 * t;
                double x = 0.5 * ((2 * p1.getX()) + (-p0.getX() + p2.getX()) * t
                        + (2 * p0.getX() - 5 * p1.getX() + 4 * p2.getX() - p3.getX()) * t2
                        + (-p0.getX() + 3 * p1.getX() - 3 * p2.getX() + p3.getX()) * t3);
                double y = 0.5 * ((2 * p1.getY()) + (-p0.getY() + p2.getY()) * t
                        + (2 * p0.getY() - 5 * p1.getY() + 4 * p2.getY() - p3.getY()) * t2
                        + (-p0.getY() + 3 * p1.getY() - 3 * p2.getY() + p3.getY()) * t3);
                out.add(new Point2D(x, y));
            }
        }
        return out;
    }

    // Douglas-Peucker 简化
    private static List<Point2D> douglasPeucker(List<Point2D> pts, double eps) {
        if (pts.size() < 3)
            return new ArrayList<>(pts);
        double maxDist = 0;
        int idx = 0;
        Point2D a = pts.get(0), b = pts.get(pts.size() - 1);
        for (int i = 1; i < pts.size() - 1; i++) {
            double d = perpendicularDistance(pts.get(i), a, b);
            if (d > maxDist) {
                maxDist = d;
                idx = i;
            }
        }
        if (maxDist > eps) {
            var left = douglasPeucker(pts.subList(0, idx + 1), eps);
            var right = douglasPeucker(pts.subList(idx, pts.size()), eps);
            List<Point2D> res = new ArrayList<>(left);
            res.addAll(right.subList(1, right.size()));
            return res;
        }
        return Arrays.asList(a, b);
    }

    private static double perpendicularDistance(Point2D p, Point2D a, Point2D b) {
        double num = Math.abs((b.getY() - a.getY()) * p.getX()
                - (b.getX() - a.getX()) * p.getY()
                + b.getX() * a.getY()
                - b.getY() * a.getX());
        double den = a.distance(b);
        return num / den;
    }
}
