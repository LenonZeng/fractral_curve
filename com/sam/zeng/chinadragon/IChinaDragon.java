package com.sam.zeng.chinadragon;

import com.sam.zeng.DegreeObserver;
import com.sam.zeng.koch.Point;

import java.util.ArrayList;

public interface IChinaDragon {
     void generate(int n, Point<Double> start, Point<Double> end, double alpha);

    ArrayList<Point> point();

    void registerDegreeObserver(DegreeObserver observer);

    void removeDegreeObserver(DegreeObserver observer);

    void degreeNotify();

    void draw();
}
