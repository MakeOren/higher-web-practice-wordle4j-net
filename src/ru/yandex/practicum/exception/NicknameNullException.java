package ru.yandex.practicum.exception;

public class NicknameNullException extends RuntimeException{
    public NicknameNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public NicknameNullException(String message) {
        super(message);
    }
}
