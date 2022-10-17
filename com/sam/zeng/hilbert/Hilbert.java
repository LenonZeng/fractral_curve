package com.sam.zeng.hilbert;

import com.sam.zeng.DegreeObserver;
import com.sam.zeng.koch.Point;
import com.sam.zeng.koch.StdDraw;

import java.util.ArrayList;

public class Hilbert implements IHilbertCurve {
    private ArrayList<Point> pList;
    private int n;
    private Point<Integer> start;

    public Hilbert(int n, Point<Integer> start) {
        pList = new ArrayList<>();
        this.n = n;
        this.start = start;
        generate(n, start);
    }

    @Override
    public void generate(int n, Point<Integer> start) {
        for (int d = 0; d < n * n; d++) {
            pList.add(d2xy(n, d, new Point(Integer.valueOf(0), Integer.valueOf(0))));
        }
    }

    @Override
    public ArrayList<Point> point() {
        return pList;
    }

    @Override
    public void registerDegreeObserver(DegreeObserver observer) {

    }

    @Override
    public void removeDegreeObserver(DegreeObserver observer) {

    }

    @Override
    public void degreeNotify() {

    }

    /**
     * 将平面上的点对，转化为一维数组上的点；
     *
     * @param n
     * @param p
     * @return
     */
    public static int xy2d(int n, Point<Integer> p) {
        int rx, ry, s, d = 0;
        for (s = n / 2; s > 0; s /= 2) {
            rx = (p.getX().intValue() & s) > 0 ? 1 : 0;
            ry = (p.getY().intValue() & s) > 0 ? 1 : 0;
            d += s * s * ((3 * rx) ^ ry);
            rot(n, p, rx, ry);
        }
        return d;
    }

    /**
     * 将一维数轴上的点，转化为二维平面上的点
     *
     * @param n
     * @param d
     * @param p
     */
    public static Point d2xy(int n, int d, Point<Integer> p) {
        int rx, ry, s, t = d;
        for (s = 1; s < n; s *= 2) {
            rx = 1 & (t / 2);
            ry = 1 & (t ^ rx);
            rot(s, p, rx, ry);

            int xVal = p.getX().intValue() + s * rx;
            int yVal = p.getY().intValue() + s * ry;
            p.setX(Integer.valueOf(xVal));
            p.setY(Integer.valueOf(yVal));
/*            p.x += s * rx;
            p.y += s * ry;*/
            t /= 4;
        }
        System.out.println("px=" + p.getX()+ " ,py=" + p.getY());
        return p;
    }

    private static void rot(int n, Point<Integer> p, int rx, int ry) {
        int px = p.getX().intValue();
        int py = p.getY().intValue();
        if (ry == 0) {
            if (rx == 1) {
                px = n-1- px;
                py = n-1- py;
          /*      p.x = n - 1 - p.x;
                p.y = n - 1 - p.y;*/
            }
            int t = py;
            py = px;
            px = t;
      /*      int t = p.y;
            p.y = p.x;
            p.x = t;*/
        }
        p.setX(Integer.valueOf(px));
        p.setY(Integer.valueOf(py));
    }

/*    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }*/

    @Override
    public void draw() {
        double s = 1.0 / n;
        StdDraw.clear();
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setXscale(-0.5, 1.5);
        StdDraw.setYscale(-0.5, 1.5);
        for (int i = 0; i < pList.size() - 1; i++) {
            Point p0 = pList.get(i);
            Point p1 = pList.get(i + 1);
            System.out.println("draw px=" + (Integer)p0.getX()*s + " ,py=" + (Integer)p0.getY()*s);
            StdDraw.line(((Integer) p0.getX()).intValue() * s, (Integer)((Integer) p0.getY()).intValue() * s,
                    ((Integer)p1.getX()).intValue() * s, ((Integer)p1.getY()).intValue() * s);
        }
    }
}
