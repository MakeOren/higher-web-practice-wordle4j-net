package ru.yandex.practicum.server.model;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStats that = (PlayerStats) o;
        return Objects.equals(nickname, that.nickname) && Objects.equals(games, that.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, games);
    }
}



