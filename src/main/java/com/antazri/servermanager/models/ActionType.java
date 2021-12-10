package com.antazri.servermanager.models;

public enum ActionType {

    INSTALL("install"),
    UPDATE("update"),
    REMOVE("remove"),
    START("start"),
    PAUSE("pause"),
    STOP("stop"),
    RUNNING("running");

    private String name;

    ActionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
