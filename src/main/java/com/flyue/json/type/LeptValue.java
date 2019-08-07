package com.flyue.json.type;

/**
 * @Author: Liu Yuefei
 * @Date: Created in 2019/8/3 20:43
 * @Description:
 */
public class LeptValue {


    private double n;

    private String s;

    private LeptType type;

    public LeptType getType() {
        return type;
    }

    public void setType(LeptType type) {
        this.type = type;
    }

    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
