package com.project.restaurant.base.dtos;

public enum Roles {
    USER("user"),
    SELLER("seller"),
    ADMIN("admin");

    private final String text;

    Roles(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
