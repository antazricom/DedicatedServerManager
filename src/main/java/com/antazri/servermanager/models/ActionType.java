package com.antazri.servermanager.models;

public enum ActionType {

    INSTALL("INSTALL"),
    UPDATE("UPDATE"),
    REMOVE("REMOVE"),
    START("START"),
    PAUSE("PAUSE"),
    STOP("STOP"),
    RUNNING("RUNNING"),
    TEST("TEST");

    private String name;

    ActionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
