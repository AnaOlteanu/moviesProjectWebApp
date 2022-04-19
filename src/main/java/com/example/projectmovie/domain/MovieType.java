package com.example.projectmovie.domain;

public enum MovieType {
    LONG("Long"), SHORT("Short");

    private String description;

    public String getDescription() {
        return description;
    }

    MovieType(String description){ this.description = description;}
}
