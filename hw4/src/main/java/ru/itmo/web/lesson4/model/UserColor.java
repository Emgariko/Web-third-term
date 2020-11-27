package ru.itmo.web.lesson4.model;

public enum UserColor {
    RED, BLUE, GREEN;

    public String toCssClass() {
        return toString().charAt(0) + toString().toLowerCase().substring(1);
    }
}