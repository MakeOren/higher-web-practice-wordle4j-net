package ru.yandex.practicum.server.handler;

public enum HttpStatusCode {
    OK(200, "OK");

    HttpStatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    private final int code;
    private final String description;
}
