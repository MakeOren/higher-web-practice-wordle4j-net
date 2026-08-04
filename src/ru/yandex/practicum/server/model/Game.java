package ru.yandex.practicum.server.model;

public class Game {
    private final int stepCount;
    private final boolean isSuggestionUsed;

    public Game(int stepCount, boolean isSuggestionUsed) {
        this.stepCount = stepCount;
        this.isSuggestionUsed = isSuggestionUsed;
    }

    public int getStepCount() {
        return stepCount;
    }

    public boolean isSuggestionUsed() {
        return isSuggestionUsed;
    }
}

