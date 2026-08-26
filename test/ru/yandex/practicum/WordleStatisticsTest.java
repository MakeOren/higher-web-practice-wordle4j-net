package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.server.WordleStatistics;
import ru.yandex.practicum.server.model.PlayerStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WordleStatisticsTest {

    private Map<String, PlayerStats> playerStatsMap;
    WordleStatistics wordleStatistics;

    @BeforeEach
    void setUp() {
        playerStatsMap = new HashMap<>();
        wordleStatistics = new WordleStatistics(playerStatsMap);
    }

    @Test
    void addResult_ifNewNickname_addPlayer_else_AddGame() {
        wordleStatistics.addResult("player1", 3, true);
        wordleStatistics.addResult("player1", 4, false);
        wordleStatistics.addResult("player2", 2, true);

        Map<String, PlayerStats> statistics = wordleStatistics.getAllStats();

        PlayerStats playerStats1 = statistics.get("player1");
        PlayerStats playerStats2 = statistics.get("player2");

        assertEquals("player1", playerStats1.getNickname());
        assertEquals("player2", playerStats2.getNickname());

        assertEquals(2, playerStats1.getGames().size());
        assertEquals(1, playerStats2.getGames().size());
    }

    @Test
    void getLeaderboard_add11Players_returnCorrectLeaderboard() {
        for (int i = 1; i < 12; i++) {
            for (int j = 0; j < 1 + i; j++) {
                wordleStatistics.addResult("player" + i, 3, true);
            }
        }

        List<WordleStatistics.LeaderboardEntry> leaderboard = new ArrayList<>(wordleStatistics.getLeaderboard());

        for (int i = 0; i < leaderboard.size(); i++) {
            int playerIndex = 11 - i;
            int winCount = 12 - i;
            assertEquals("player" + playerIndex, leaderboard.get(i).nickname());
            assertEquals(winCount, leaderboard.get(i).wins());
            assertNotEquals("player1", leaderboard.get(i).nickname());
        }


    }

}
