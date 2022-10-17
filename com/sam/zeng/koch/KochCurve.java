package com.sam.zeng.koch;

import com.sam.zeng.DegreeObserver;

import java.util.ArrayList;

public class KochCurve implements IKochCurve {
    private ArrayList<Point<Double>> pList;
    private int n;
    public static int UP = 1, DOWN = -1;
    private Point sp, ep;
    //koch curve阶数

    public KochCurve(int n, Point a, Point b) {
        this.pList = new ArrayList<>();
        this.sp = a;
        this.ep = b;
        this.n = n;
        pList.add(a);
        generate(n,sp, ep);
        pList.add(b);
    }

    @Override
    public void generate(int n, Point<Double> a, Point<Double> b, double sign) {
        if (n == 0) {
            return;
        }
        Point s = new Point(0, 0), t = new Point(0, 0),
                u = new Point(0, 0), mid = new Point(0, 0);

        //distance formula of the overall line
        double distance = Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));

        //find the points for the first third of the line
        s.setX(a.getX() + ((b.getX() - a.getX()) / 3));
        s.setY(a.getY() + ((b.getY() - a.getY()) / 3));

        //find the points for the second third of the line
        t.setX(a.getX() + (2 * ((b.getX() - a.getX()) / 3)));
        t.setY(a.getY() + (2 * ((b.getY() - a.getY()) / 3)));

        //finding the points for the middle of the line
        mid.setX((a.getX() + b.getX()) / 2);
        mid.setY((a.getY() + b.getY()) / 2);

        //finding the angle of the whole line compared to the x-axis
        double theta1 = Math.atan2(b.getY() - a.getY(), b.getX() - a.getX());

        //finding the angle to put the top of the triangle point at
        double theta2 = theta1 + sign;

        //finding the value of the base of the 30-60-90 triangle
        double base_triangle = (distance / 6);

        //finding the height of the 30-60-90 triangle
        double height_triangle = base_triangle * Math.sqrt(3);

        //finding the points for the top of the bump
        u.setX((Double)mid.getX() + (height_triangle * Math.cos(theta2)));
        u.setY((Double)mid.getY() + (height_triangle * Math.sin(theta2)));

        generate(n - 1, a, s, sign);
        pList.add(s);

        //System.out.println("s.x=" + s.getX() + ", s.y=" + s.getY());
        generate(n - 1, s, u, sign);
        pList.add(u);
        //System.out.println("u.x=" + u.getX() + ", u.y=" + u.getY());
        generate(n - 1, u, t, sign);
        pList.add(t);
        //System.out.println("t.x=" + t.getX() + ", t.y=" + t.getY());
        generate(n - 1, t, b, sign);
    }

    @Override
    public void generate(int n, Point<Double> a, Point<Double> b) {
        if (n == 0) {
            return;
        }
        Point s = new Point(0.0, 0.0), t = new Point(0.0, 0.0),
                u = new Point(0.0, 0.0), mid = new Point(0.0, 0.0);

        //distance formula of the overall line
        double distance = Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));

        //find the points for the first third of the line
        s.setX(a.getX() + ((b.getX() - a.getX()) / 3));
        s.setY(a.getY() + ((b.getY() - a.getY()) / 3));

        //find the points for the second third of the line
        t.setX(a.getX() + (2 * ((b.getX() - a.getX()) / 3)));
        t.setY(a.getY() + (2 * ((b.getY() - a.getY()) / 3)));

        //finding the points for the middle of the line
        mid.setX((a.getX() + b.getX()) / 2);
        mid.setY((a.getY() + b.getY()) / 2);

        //finding the angle of the whole line compared to the x-axis
        double theta1 = Math.atan2(b.getY() - a.getY(), b.getX() - a.getX());

        //finding the angle to put the top of the triangle point at
   /*     double theta2 = theta1 + (Math.PI / 2);*/
        double theta2 = theta1 - Math.PI/3;
        //finding the value of the base of the 30-60-90 triangle
        double base_triangle = (distance / 6);
        double edge_triangle = base_triangle * 2;
        u.setX((Double)s.getX() + edge_triangle*Math.cos(theta2));
        u.setY((Double)s.getY() + edge_triangle*Math.sin(theta2));
/*
        //finding the height of the 30-60-90 triangle
        double height_triangle = base_triangle * Math.sqrt(3);

        //finding the points for the top of the bump
        u.setX(mid.getX() + (height_triangle * Math.cos(theta2)));
        u.setY(mid.getY() + (height_triangle * Math.sin(theta2)));*/

        generate(n - 1, a, s);
        pList.add(s);
        //System.out.println("s.x=" + s.getX() + ", s.y=" + s.getY());
        generate(n - 1, s, u);
        pList.add(u);
        //System.out.println("u.x=" + u.getX() + ", u.y=" + u.getY());
        generate(n - 1, u, t);
        pList.add(t);
        //System.out.println("t.x=" + t.getX() + ", t.y=" + t.getY());
        generate(n - 1, t, b);
    }

    @Override
    public int edges() {
        return (int) Math.pow(4, n);
    }

    @Override
    public int vertex() {
        return edges() + 1;
    }

    @Override
    public ArrayList<Point> points() {
        ArrayList<Point> result = new ArrayList<>();
        result.add(sp);
        result.addAll(pList);
        result.add(ep);
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
        StdDraw.setScale(-1.0, 1.0);
        for (int i = 0; i < pList.size() - 1; i++) {
            Point p0 = pList.get(i);
            Point p1 = pList.get(i + 1);
            System.out.println("draw px=" + p0.getX() + " ,py=" + p0.getY());
            StdDraw.line((Double)p0.getX(), (Double)p0.getY(), (Double)p1.getX(), (Double)p1.getY());
        }
    }



}
