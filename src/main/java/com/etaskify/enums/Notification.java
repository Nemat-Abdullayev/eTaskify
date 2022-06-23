package com.etaskify.enums;

public enum Notification {
    TASK_ASSIGNED(0), TASK_DELETED(1), TASK_CHANGED(2);

    private final int value;


    Notification(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
