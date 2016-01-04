package com.skola.helpClasses;

/**
 * Created with IntelliJ IDEA.
 * User: seb
 * Date: 2013-09-13
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class Pair <T1, T2> {
    private T1 fst;
    private T2 snd;

    public Pair() { }

    public Pair(T1 x1, T2 x2) {
        fst = x1;
        snd = x2;
    }

    public void setFirst(T1 x) {
        fst = x;
    }

    public void setSecond(T2 x) {
        snd = x;
    }

    public T1 first() {
        return fst;
    }

    public T2 second() {
        return snd;
    }
}