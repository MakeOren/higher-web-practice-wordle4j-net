package ru.yandex.practicum.server.model;

import java.util.List;

public class PlayerStats {
    private final String nickname;
    private final  List<Game> games;

    public PlayerStats(String nickname, List<Game> games) {
        this.nickname = nickname;
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    public String getNickname() {
        return nickname;
    }
}



