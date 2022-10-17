package com.sam.zeng.hilbert;

import com.sam.zeng.DegreeObserver;
import com.sam.zeng.koch.Point;

import java.util.ArrayList;

public interface IHilbertCurve {
    void generate(int n, Point<Integer> start);

    ArrayList<Point> point();

    void registerDegreeObserver(DegreeObserver observer);

    void removeDegreeObserver(DegreeObserver observer);

    void degreeNotify();

    void draw();
}
