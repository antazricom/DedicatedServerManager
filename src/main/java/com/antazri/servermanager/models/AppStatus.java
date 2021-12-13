package com.antazri.servermanager.models;

public enum AppStatus {
    ACTIVE("ACTIVE"),
    PAUSED("PAUSED"),
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ERROR("ERROR");

    private String name;

    AppStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AppStatus{" +
                "name='" + name + '\'' +
                '}';
    }
}
