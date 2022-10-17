package com.sam.zeng;

import com.sam.zeng.koch.KochType;
import com.sam.zeng.koch.Point;
import com.sam.zeng.koch.ShapeObserver;

import java.util.ArrayList;

public interface IKochModel {
    void increaseDegree();
    void decreaseDegree();
    void setDegree(int n);
    int getDegree();
    void setShape(KochType type);
    KochType getShape();
    ArrayList<Point> getPoints();
    void registerDegreeObserver(DegreeObserver observer);
    void removeDegreeObserver(DegreeObserver observer);
    void degreeNotify();

    void registerShapeObserver(ShapeObserver observer);
    void removeShapeObserver(ShapeObserver observer);
}
