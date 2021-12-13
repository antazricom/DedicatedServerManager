package com.antazri.servermanager.models;

public enum AppStatus {
    ACTIVE("active"),
    PAUSED("paused"),
    ENABLE("enable"),
    DISABLE("disable"),
    ERROR("error");

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
