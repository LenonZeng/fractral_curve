package com.sam.zeng.koch;

import com.sam.zeng.DegreeObserver;

import java.util.ArrayList;

public interface IKochCurve {
    void generate(int n, Point<Double> a, Point<Double> b);
    void generate(int n, Point<Double> a, Point<Double> b, double sign);
    int edges();
    int vertex();
    ArrayList<Point> points();
    ArrayList<Point> snowFlake();

    void registerDegreeObserver(DegreeObserver observer);
    void removeDegreeObserver(DegreeObserver observer);
    void degreeNotify();

    void registerShapeObserver(ShapeObserver observer);
    void removeShapeObserver(ShapeObserver observer);
    void shapeNotify();
    void draw();
}
