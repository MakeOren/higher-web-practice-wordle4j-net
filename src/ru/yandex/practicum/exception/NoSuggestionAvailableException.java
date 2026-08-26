package ru.yandex.practicum.exception;

public class NoSuggestionAvailableException extends RuntimeException{
    public NoSuggestionAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuggestionAvailableException(String message) {
        super(message);
    }
}
