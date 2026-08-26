package ru.yandex.practicum.exception;

public class WordNotFoundDictionaryException extends RuntimeException{
    public WordNotFoundDictionaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordNotFoundDictionaryException(String message) {
        super(message);
    }
}