package com.etaskify.enums;

public enum RoleName {

    ADMIN(0), USER(1),MANAGER(3);

    private final int value;

    RoleName(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
