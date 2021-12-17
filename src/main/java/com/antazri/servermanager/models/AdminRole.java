package com.antazri.servermanager.models;

public enum AdminRole {

    OWNER("OWNER"),
    MAINTAINER("MAINTAINER"),
    DEVELOPER("DEVELOPER");

    private String name;

    AdminRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "name='" + name + '\'' +
                '}';
    }
}
