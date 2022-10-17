package com.sam.zeng;

import com.sam.zeng.koch.KochType;

public class KochController implements IKochController {
    private IKochModel model;
    private KochView view;

    public KochController(IKochModel model) {
        this.model = model;
        this.view = new KochView(this, model);
        view.createView();
        view.createControls();
    }

    @Override
    public void start() {
        view.drawShape(model.getShape());
    }

    @Override
    public void clear() {
        view.clear();
    }

    @Override
    public void increaseDegree() {
        model.increaseDegree();
    }

    @Override
    public void decreaseDegree() {
        model.decreaseDegree();
    }

    @Override
    public void changeShape(KochType type) {

    }

    @Override
    public void setDegree(int degree) {
        model.setDegree(degree);
    }

    @Override
    public void setType(KochType type) {
        model.setShape(type);
    }
}
