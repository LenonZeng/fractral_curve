package com.sam.zeng.koch;

public enum KochType {
    Koch("koch curve"),Hilbert("hilbert curve"),Line("line"),Triangle("triangle"),Square("square"),
    Star("star"),Snowflake("snowflake curve"),Dragon("dragon curve"),Peano("peano curve");
    private String type;
    KochType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
