package ru.yandex.practicum.exception;

public class InvalidQueryParameterException  extends RuntimeException{
    public InvalidQueryParameterException (String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidQueryParameterException (String message) {
        super(message);
    }
}
