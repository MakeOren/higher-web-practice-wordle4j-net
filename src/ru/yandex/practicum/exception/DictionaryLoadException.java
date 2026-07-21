package ru.yandex.practicum.exception;

public class DictionaryLoadException extends RuntimeException{
    public DictionaryLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
