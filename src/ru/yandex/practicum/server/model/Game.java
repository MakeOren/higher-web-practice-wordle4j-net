package ru.yandex.practicum.server.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return stepCount == game.stepCount && isSuggestionUsed == game.isSuggestionUsed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepCount, isSuggestionUsed);
    }
}

