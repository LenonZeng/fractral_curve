package com.sam.zeng;

import com.sam.zeng.koch.KochType;

public class FractalTDD {
    public static void main(String[] args) {
        KochModel model = new KochModel();
        KochController kochController = new KochController(model);
        kochController.setType(KochType.Koch);
        kochController.setDegree(4);
        kochController.start();
    }
}
