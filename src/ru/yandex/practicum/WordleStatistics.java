package ru.yandex.practicum;

import ru.yandex.practicum.model.Game;
import ru.yandex.practicum.model.PlayerStats;

import java.util.*;

public class WordleStatistics {

    private final static int LEADER_BOARD_COUNT = 10;

    public record LeaderboardEntry(String nickname, int wins) {

    }

    private final Map<String, PlayerStats> statistics;

    WordleStatistics(Map<String, PlayerStats> statistics) {
        this.statistics = statistics;
    }

    public void addResult(String nickname, int steps, boolean usedHints) {
        Game game = new Game(steps, usedHints);

        if (statistics.containsKey(nickname)) {
            PlayerStats playerStats = statistics.get(nickname);
            List<Game> updateGameList = new ArrayList<>(playerStats.games());

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
        List<LeaderboardEntry> leaderboardEntryList = statistics.values()
                .stream()
                .sorted(Comparator.comparingInt((PlayerStats playerStats)-> playerStats.games().size()).reversed())
                .map((playerStats -> new LeaderboardEntry(playerStats.nickname(), playerStats.games().size())))
                .limit(LEADER_BOARD_COUNT)
                .toList();

        return leaderboardEntryList;
    }

    public PlayerStats getPlayerStats(String nickname) {
        return statistics.get(nickname);
    }

    public HashMap<String, PlayerStats> getAllStats() {
        return new HashMap<>(statistics);
    }

}
