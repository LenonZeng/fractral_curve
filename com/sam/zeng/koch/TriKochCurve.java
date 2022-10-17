package com.sam.zeng.koch;

import com.sam.zeng.DegreeObserver;

import java.util.ArrayList;

public class TriKochCurve implements IKochCurve {
    private KochCurve kochA, kochB, kochC;
    private ArrayList<Point> result;

    @Override
    public void generate(int n, Point a, Point b, double sign) {

    }

    @Override
    public void generate(int n, Point a, Point b) {
    }

    public TriKochCurve(int n, Point a, Point b, Point c) {
        result = new ArrayList<>();
        kochA = new KochCurve(n, a, b);
        kochB = new KochCurve(n, b, c);
        kochC = new KochCurve(n, c, a);
        points();
    }

    @Override
    public int edges() {
        return 0;
    }

    @Override
    public int vertex() {
        return 0;
    }

    @Override
    public ArrayList<Point> points() {
        result = new ArrayList<>();
        result.addAll(kochA.points());
        result.addAll(kochB.points());
        result.addAll(kochC.points());
        return result;
    }

    @Override
    public ArrayList<Point> snowFlake() {
        return null;
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
    public void registerShapeObserver(ShapeObserver observer) {

    }

    @Override
    public void removeShapeObserver(ShapeObserver observer) {

    }

    @Override
    public void shapeNotify() {

    }

    @Override
    public void draw() {
        StdDraw.clear();
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setXscale(-2.0, 2.0);
        StdDraw.setYscale(-2.0, 2.0);
        for (int i = 0; i < result.size() - 1; i++) {
            Point p0 = result.get(i);
            Point p1 = result.get(i + 1);
            System.out.println("draw px=" + p0.getX() + " ,py=" + p0.getY());
            StdDraw.line((Double)p0.getX(), (Double)p0.getY(), (Double)p1.getX(), (Double)p1.getY());
        }
    }
}
