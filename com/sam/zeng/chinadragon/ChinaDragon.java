package com.sam.zeng.chinadragon;

import com.sam.zeng.DegreeObserver;
import com.sam.zeng.koch.Point;
import com.sam.zeng.koch.StdDraw;

import java.util.ArrayList;

public class ChinaDragon implements IChinaDragon {
    private ArrayList<Point> pList;
    private int n;
    private Point start, end;

    public ChinaDragon(int n, Point start, Point end) {
        this.pList = new ArrayList<>();
        this.start = start;
        this.end = end;
        this.n = n;
        pList.add(start);
        generate(n, start, end, Math.PI / 2);
        pList.add(end);
    }

    @Override
    public void generate(int n, Point<Double> start, Point<Double> end, double alpha) {
        if (n == 0) return;
        Point p = new Point(0, 0);
        double x0 = start.getX() + (end.getX() - start.getX()) / 2.0;
        double y0 = start.getY() + (end.getY() - start.getY()) / 2.0;
        double theta = Math.atan2((end.getY() - start.getY()), (end.getX() - start.getX()));
        double d = Math.sqrt(Math.pow(end.getY() - start.getY(), 2) + Math.pow(end.getX() - start.getX(), 2));
        double beta = alpha + theta;
        p.setX(x0 + d / 2.0 * Math.cos(beta));
        p.setY(y0 + d / 2.0 * Math.sin(beta));

        generate(n - 1, start, p, Math.PI / 2);
        pList.add(p);
        System.out.println("gen px=" + p.getX() + " ,py=" + p.getY());
        generate(n - 1, p, end, -Math.PI / 2);
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

    @Override
    public void draw() {
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-1.0, 1.0);
        for (int i = 0; i < pList.size() - 1; i++) {
            Point p0 = pList.get(i);
            Point p1 = pList.get(i + 1);
            System.out.println("draw px=" + p0.getX() + " ,py=" + p0.getY());
            StdDraw.line((Double) p0.getX(), (Double)p0.getY(), (Double)p1.getX(), (Double)p1.getY());
        }
    }
}
