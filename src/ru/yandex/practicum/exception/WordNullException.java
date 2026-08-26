package ru.yandex.practicum.exception;

public class WordNullException extends RuntimeException{
    public WordNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordNullException(String message) {
        super(message);
    }
}
