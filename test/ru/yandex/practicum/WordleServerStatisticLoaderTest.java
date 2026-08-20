package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.server.WordleServerStatisticLoader;
import ru.yandex.practicum.server.model.Game;
import ru.yandex.practicum.server.model.PlayerStats;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleServerStatisticLoaderTest {

    private final static String STATISTICS_TEST_PATH = "statistics_test.json";

    @Test
    void saveAndLoadStatistics_returnsSameData_afterRoundTrip() {
        Map<String, PlayerStats> playerStatsMap = new HashMap<>();
        List<Game> games1 = new ArrayList<>();
        List<Game> games2 = new ArrayList<>();
        List<Game> games3 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            games1.add(new Game(i, true));
        }

        for (int i = 0; i < 4; i++) {
            games2.add(new Game(i, true));
        }

        for (int i = 0; i < 3; i++) {
            games3.add(new Game(i, false));
        }

        PlayerStats player1 = new PlayerStats("player1", games1);
        PlayerStats player2 = new PlayerStats("player2", games2);
        PlayerStats player3 = new PlayerStats("player3", games3);

        playerStatsMap.put("player1", player1);
        playerStatsMap.put("player2", player2);
        playerStatsMap.put("player3", player3);

        WordleServerStatisticLoader.saveStatistics(playerStatsMap, STATISTICS_TEST_PATH);
        Map<String, PlayerStats> playerStatsMapAfterLoad = WordleServerStatisticLoader.loadStatistics(STATISTICS_TEST_PATH);

        for (Map.Entry<String, PlayerStats> playerStatsEntry : playerStatsMap.entrySet()) {
            String key = playerStatsEntry.getKey();

            assertEquals(playerStatsMap.get(key), playerStatsMapAfterLoad.get(key));
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File(STATISTICS_TEST_PATH);
        file.delete();
    }
}
