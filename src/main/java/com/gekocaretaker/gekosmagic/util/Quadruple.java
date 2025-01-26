package com.gekocaretaker.gekosmagic.util;

public class Quadruple<A, B, C, D> {
    private A first;
    private B second;
    private C third;
    private D fourth;

    public Quadruple(A first, B second, C third, D fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public A getFirst() {
        return this.first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return this.second;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public C getThird() {
        return this.third;
    }

    public void setThird(C third) {
        this.third = third;
    }

    public D getFourth() {
        return this.fourth;
    }

    public void setFourth(D fourth) {
        this.fourth = fourth;
    }
}
