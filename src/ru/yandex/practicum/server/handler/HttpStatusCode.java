package ru.yandex.practicum.server.handler;

public enum HttpStatusCode {
    OK(200, "OK"),
    NOT_FOUND(404,"Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_ERROR(500, "Internal Error");

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
