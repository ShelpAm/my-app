// package com.mycompany.app;
//
// import javafx.scene.image.Image;
// import javafx.scene.image.PixelReader;
// import javafx.scene.shape.MoveTo;
// import javafx.scene.shape.ClosePath;
// import javafx.scene.shape.LineTo;
// import javafx.scene.shape.Path;
//
// import java.util.ArrayList;
// import java.util.List;
//
// public class ContourClipper {
//
//     /**
//      * 从 PNG 里提取边界轮廓（顺时针），并返回简化后的 Path。
//      *
//      * @param img     要裁剪的带透明通道的 Image
//      * @param epsilon Douglas–Peucker 简化参数（越小保留越多点，典型 1–5）
//      * @return JavaFX Path，可直接用 setClip()
//      */
//     public static Path buildClippingPath(Image img, double epsilon) {
//         int w = (int) img.getWidth();
//         int h = (int) img.getHeight();
//         PixelReader pr = img.getPixelReader();
//
//         // 1) 把 alpha > 0 的像素当作“实心区”，其余作“背景”
//         boolean[][] solid = new boolean[h][w];
//         for (int y = 0; y < h; y++) {
//             for (int x = 0; x < w; x++) {
//                 int alpha = (pr.getArgb(x, y) >> 24) & 0xFF;
//                 solid[y][x] = alpha > 0;
//             }
//         }
//
//         // 2) Moore–邻域轮廓跟踪，找外层边界
//         List<int[]> contour = traceContour(solid, w, h);
//
//         // 3) Douglas–Peucker 简化
//         List<int[]> simple = douglasPeucker(contour, epsilon);
//
//         // 4) 构建 JavaFX Path
//         Path path = new Path();
//         if (simple.isEmpty())
//             return path;
//         // MoveTo 第一个点
//         path.getElements().add(new MoveTo(simple.get(0)[0], simple.get(0)[1]));
//         // LineTo 后续
//         for (int i = 1; i < simple.size(); i++) {
//             path.getElements().add(new LineTo(simple.get(i)[0], simple.get(i)[1]));
//         }
//         // 自动闭合
//         path.getElements().add(new LineTo(simple.get(0)[0], simple.get(0)[1]));
//
//         path.getElements().add(new ClosePath()); // Patch from ShelpAm, to
//         // close the path.
//         return path;
//     }
//
//     // Moore‐邻域法寻找首个边界点，并顺时针跟踪
//     private static List<int[]> traceContour(boolean[][] solid, int w, int h) {
//         // 8 个方向
//         int[][] dirs = { { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
//         int startX = -1, startY = -1;
//         outer: for (int y = 0; y < h; y++) {
//             for (int x = 0; x < w; x++) {
//                 if (solid[y][x]) {
//                     // 该点有至少一个邻居是“背景”，就是边界
//                     for (int[] d : dirs) {
//                         int ny = y + d[1], nx = x + d[0];
//                         if (nx < 0 || ny < 0 || nx >= w || ny >= h || !solid[ny][nx]) {
//                             startX = x;
//                             startY = y;
//                             break outer;
//                         }
//                     }
//                 }
//             }
//         }
//         if (startX < 0)
//             return List.of();
//
//         List<int[]> contour = new ArrayList<>();
//         int cx = startX, cy = startY, dir = 0;
//         do {
//             contour.add(new int[] { cx, cy });
//             // 从当前方向的前一个方向开始逆时针找下一个边界点
//             int searchDir = (dir + 6) % 8;
//             for (int i = 0; i < 8; i++) {
//                 int d = (searchDir + i) % 8;
//                 int nx = cx + dirs[d][0], ny = cy + dirs[d][1];
//                 if (nx >= 0 && ny >= 0 && nx < w && ny < h && solid[ny][nx]) {
//                     cx = nx;
//                     cy = ny;
//                     dir = d;
//                     break;
//                 }
//             }
//         } while (!(cx == startX && cy == startY));
//         return contour;
//     }
//
//     // Douglas–Peucker 算法
//     private static List<int[]> douglasPeucker(List<int[]> points, double epsilon) {
//         if (points.size() < 3)
//             return points;
//         int idx = -1;
//         double maxDist = 0;
//         int[] a = points.get(0), b = points.get(points.size() - 1);
//         for (int i = 1; i < points.size() - 1; i++) {
//             double d = perpendicularDistance(points.get(i), a, b);
//             if (d > maxDist) {
//                 idx = i;
//                 maxDist = d;
//             }
//         }
//         if (maxDist > epsilon) {
//             var left = douglasPeucker(points.subList(0, idx + 1), epsilon);
//             var right = douglasPeucker(points.subList(idx, points.size()), epsilon);
//             // 合并（去重 idx）
//             List<int[]> result = new ArrayList<>(left);
//             result.addAll(right.subList(1, right.size()));
//             return result;
//         }
//         // 直接用端点
//         return List.of(a, b);
//     }
//
//     private static double perpendicularDistance(int[] p, int[] a, int[] b) {
//         double x0 = p[0], y0 = p[1];
//         double x1 = a[0], y1 = a[1], x2 = b[0], y2 = b[1];
//         double num = Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1);
//         double den = Math.hypot(y2 - y1, x2 - x1);
//         return num / den;
//     }
// }
//

package com.mycompany.app;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.List;

public class ContourClipper {

    /**
     * 从 PNG 里提取边界轮廓（顺时针），并返回简化后的 Path。
     *
     * @param img     要裁剪的带透明通道的 Image
     * @param epsilon Douglas–Peucker 简化参数（越小保留越多点，典型 1–5）
     * @return JavaFX Path，可直接用 setClip()
     */
    public static Path buildClippingPath(Image img, double epsilon) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();
        PixelReader pr = img.getPixelReader();

        // 1) 把 alpha > 10 的像素当作“实心区”，其余作“背景”
        boolean[][] solid = new boolean[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int alpha = (pr.getArgb(x, y) >> 24) & 0xFF;
                solid[y][x] = alpha > 10;
            }
        }

        // 2) Moore–邻域轮廓跟踪，找外层边界
        List<int[]> contour = traceContour(solid, w, h);

        // 3) Douglas–Peucker 简化
        List<int[]> simple = douglasPeucker(contour, epsilon);

        // 4) 构建 JavaFX Path
        Path path = new Path();
        if (simple.isEmpty())
            return path;

        path.getElements().add(new MoveTo(simple.get(0)[0], simple.get(0)[1]));
        for (int i = 1; i < simple.size(); i++) {
            path.getElements().add(new LineTo(simple.get(i)[0], simple.get(i)[1]));
        }
        path.getElements().add(new ClosePath());

        // 可视化调试：边框
        path.setStroke(javafx.scene.paint.Color.RED);
        path.setStrokeWidth(1);
        path.setFill(javafx.scene.paint.Color.TRANSPARENT);

        return path;
    }

    // Moore‐邻域法寻找首个边界点，并顺时针跟踪
    private static List<int[]> traceContour(boolean[][] solid, int w, int h) {
        int[][] dirs = { { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
        int startX = -1, startY = -1;
        outer: for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (solid[y][x]) {
                    for (int[] d : dirs) {
                        int ny = y + d[1], nx = x + d[0];
                        if (nx < 0 || ny < 0 || nx >= w || ny >= h || !solid[ny][nx]) {
                            startX = x;
                            startY = y;
                            break outer;
                        }
                    }
                }
            }
        }
        if (startX < 0)
            return List.of();

        List<int[]> contour = new ArrayList<>();
        int cx = startX, cy = startY, dir = 0;
        do {
            contour.add(new int[] { cx, cy });
            int searchDir = (dir + 6) % 8;
            for (int i = 0; i < 8; i++) {
                int d = (searchDir + i) % 8;
                int nx = cx + dirs[d][0], ny = cy + dirs[d][1];
                if (nx >= 0 && ny >= 0 && nx < w && ny < h && solid[ny][nx]) {
                    cx = nx;
                    cy = ny;
                    dir = d;
                    break;
                }
            }
        } while (!(cx == startX && cy == startY));
        return contour;
    }

    private static List<int[]> douglasPeucker(List<int[]> points, double epsilon) {
        if (points.size() < 3)
            return points;
        int idx = -1;
        double maxDist = 0;
        int[] a = points.get(0), b = points.get(points.size() - 1);
        for (int i = 1; i < points.size() - 1; i++) {
            double d = perpendicularDistance(points.get(i), a, b);
            if (d > maxDist) {
                idx = i;
                maxDist = d;
            }
        }
        if (maxDist > epsilon) {
            var left = douglasPeucker(points.subList(0, idx + 1), epsilon);
            var right = douglasPeucker(points.subList(idx, points.size()), epsilon);
            List<int[]> result = new ArrayList<>(left);
            result.addAll(right.subList(1, right.size()));
            return result;
        }
        return List.of(a, b);
    }

    private static double perpendicularDistance(int[] p, int[] a, int[] b) {
        double x0 = p[0], y0 = p[1];
        double x1 = a[0], y1 = a[1], x2 = b[0], y2 = b[1];
        double num = Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1);
        double den = Math.hypot(y2 - y1, x2 - x1);
        return num / den;
    }
}
