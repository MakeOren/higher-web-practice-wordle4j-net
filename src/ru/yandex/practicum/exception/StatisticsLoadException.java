package ru.yandex.practicum.exception;

public class StatisticsLoadException extends RuntimeException{
    public StatisticsLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatisticsLoadException(String message) {
        super(message);
    }
}

