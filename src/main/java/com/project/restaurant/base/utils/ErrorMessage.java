package com.project.restaurant.base.utils;

public enum ErrorMessage {
    NOT_FOUND("Not found"),
    DUPLICATE("Already exists"),
    CANNOT_DELETE("Cannot delete "),
    NOT_ALLOWED(" Not allowed "),
    EMPTY("Empty field");

    private final String text;

    ErrorMessage(final String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return text;
    }
}
