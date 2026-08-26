package ru.yandex.practicum.exception;

public class StatisticsSubmissionFailedException  extends RuntimeException{
    public StatisticsSubmissionFailedException (String message, Throwable cause) {
        super(message, cause);
    }

    public StatisticsSubmissionFailedException (String message) {
        super(message);
    }
}
