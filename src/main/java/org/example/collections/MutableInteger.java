package org.example.collections;

import java.util.concurrent.atomic.AtomicInteger;

public class MutableInteger {
    private int value;

    public MutableInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public MutableInteger increment() {
        value++;
        return this;
    }
}
