package com.example.projectmovie.domain;

public enum Gender {
    F("Female"), M("Male");

    private String description;

    public String getDescription() {
        return description;
    }

    Gender(String description){ this.description = description;}
}
