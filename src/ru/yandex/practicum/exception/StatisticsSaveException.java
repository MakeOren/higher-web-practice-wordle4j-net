package ru.yandex.practicum.exception;

public class StatisticsSaveException extends RuntimeException{
    public StatisticsSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatisticsSaveException(String message) {
        super(message);
    }
}

