package com.etaskify.enums;

public enum Status {
    CREATED(0), PROCESSING(1), FINISHED(3);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
