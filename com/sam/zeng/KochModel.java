package com.sam.zeng;

import com.sam.zeng.chinadragon.ChinaDragon;
import com.sam.zeng.hilbert.Hilbert;
import com.sam.zeng.koch.*;

import java.util.ArrayList;

public class KochModel implements IKochModel {
    private ArrayList<DegreeObserver> degreeObservers;
    private ArrayList<ShapeObserver> shapeObservers;
    private ArrayList<Point> points;
    private KochType type;
    private int degree = 0;

    public KochModel() {
        degreeObservers = new ArrayList<>();
        shapeObservers = new ArrayList<>();
    }

    @Override
    public void increaseDegree() {
        degree++;
        if(type !=null && type instanceof KochType){
            caculate(type, degree);
            degreeNotify();
        }
    }

    @Override
    public void decreaseDegree() {
        degree--;
        if(type !=null && type instanceof KochType){
            caculate(type, degree);
            degreeNotify();
        }
    }

    @Override
    public void setDegree(int n) {
        this.degree = n;
        if(n != 0 && type !=null && type instanceof KochType){
            caculate(type, degree);
        }
    }

    @Override
    public int getDegree() {
        return degree;
    }

    @Override
    public void setShape(KochType type) {
        this.type = type;
        if(degree != 0 && type !=null && type instanceof KochType){
            caculate(type, degree);
        }
    }

    @Override
    public KochType getShape() {
        return type;
    }

    @Override
    public ArrayList<Point> getPoints() {
        return points != null && points.isEmpty() ? null : points;
    }

    @Override
    public void registerDegreeObserver(DegreeObserver observer) {
        if (!degreeObservers.contains(observer)) {
            degreeObservers.add(observer);
        }
    }

    @Override
    public void removeDegreeObserver(DegreeObserver observer) {
        if (!(observer instanceof DegreeObserver)) return;
        if (degreeObservers.contains(observer)) {
            degreeObservers.remove(observer);
        }
    }

    @Override
    public void degreeNotify() {
        if(degreeObservers!=null && degreeObservers.size()>0){
            for(DegreeObserver observer:degreeObservers){
                observer.update(degree);
            }
        }
    }

    @Override
    public void registerShapeObserver(ShapeObserver observer) {
        if (!(observer instanceof ShapeObserver)) return;
        if (!shapeObservers.contains(observer)) {
            shapeObservers.add(observer);
        }
    }

    @Override
    public void removeShapeObserver(ShapeObserver observer) {
        if (shapeObservers.contains(observer)) {
            shapeObservers.remove(observer);
        }
    }

    private void caculate(KochType type, int degree) {
        if ("koch curve".equals(type.getType())) {
            KochCurve koch = new KochCurve(degree, new Point(Double.valueOf(-1), Double.valueOf(0)),
                    new Point(Double.valueOf(1), Double.valueOf(0)));
            points = koch.points();
        }
        if ("hilbert curve".equals(type.getType())) {
            Hilbert hilbert = new Hilbert(degree, new Point(Integer.valueOf(0), Integer.valueOf(0)));
            points = hilbert.point();
        }
        if("dragon curve".equals(type.getType())){
            ChinaDragon dragon = new ChinaDragon(degree, new Point(0.25, 0.25), new Point(0.75, 0.75));
            points = dragon.point();
        }

        if("snowflake curve".equals(type.getType())){
            TriKochCurve snowflake = new TriKochCurve(4, new Point(Double.valueOf(-1), Double.valueOf(0)),
                    new Point(Double.valueOf(1), Double.valueOf(0)), new Point(Double.valueOf(0), Double.valueOf(1.732)));
            points = snowflake.points();
        }
    }
}
