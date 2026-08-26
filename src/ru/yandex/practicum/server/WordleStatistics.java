package ru.yandex.practicum.server;

import ru.yandex.practicum.server.model.Game;
import ru.yandex.practicum.server.model.PlayerStats;

import java.util.*;

public class WordleStatistics {

    private final static int LEADER_BOARD_COUNT = 10;

    public record LeaderboardEntry(String nickname, int wins) {

    }

    private final Map<String, PlayerStats> statistics;

    public WordleStatistics(Map<String, PlayerStats> statistics) {
        this.statistics = statistics;
    }

    public void addResult(String nickname, int steps, boolean usedHints) {
        Game game = new Game(steps, usedHints);

        if (statistics.containsKey(nickname)) {
            PlayerStats playerStats = statistics.get(nickname);
            List<Game> updateGameList = new ArrayList<>(playerStats.getGames());

            updateGameList.add(game);
            statistics.put(nickname, new PlayerStats(nickname, updateGameList));
        } else {
            List<Game> games = new ArrayList<>();
            games.add(game);
            PlayerStats playerStats = new PlayerStats(nickname, games);

            statistics.put(nickname, playerStats);
        }
    }

    public Collection<LeaderboardEntry> getLeaderboard() {
        return statistics.values()
                .stream()
                .sorted(Comparator.comparingInt((PlayerStats playerStats)-> playerStats.getGames().size()).reversed())
                .map((playerStats -> new LeaderboardEntry(playerStats.getNickname(), playerStats.getGames().size())))
                .limit(LEADER_BOARD_COUNT)
                .toList();
    }

    public boolean  isPlayerOnLeaderBoard(String name, List<WordleStatistics.LeaderboardEntry> leaderboardEntries) {
        return leaderboardEntries.stream()
                .anyMatch(leaderboardEntry -> leaderboardEntry.nickname.equals(name));
    }

    public PlayerStats getPlayerStats(String nickname) {
        return statistics.get(nickname);
    }

    public HashMap<String, PlayerStats> getAllStats() {
        return new HashMap<>(statistics);
    }

}
