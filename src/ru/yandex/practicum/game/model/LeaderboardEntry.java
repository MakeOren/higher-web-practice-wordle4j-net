package ru.yandex.practicum.game.model;

public class LeaderboardEntry {

    private final String nickname;
    private final int wins;

    public LeaderboardEntry(String nickname, int wins) {
        this.nickname = nickname;
        this.wins = wins;
    }

    public String getNickname() {
        return nickname;
    }

    public int getWins() {
        return wins;
    }
}
