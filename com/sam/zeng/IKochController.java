package com.sam.zeng;

import com.sam.zeng.koch.KochType;

public interface IKochController {
    void start();
    void clear();
    void increaseDegree();
    void decreaseDegree();
    void changeShape(KochType type);
    void setDegree(int degree);
    void setType(KochType type);
}
