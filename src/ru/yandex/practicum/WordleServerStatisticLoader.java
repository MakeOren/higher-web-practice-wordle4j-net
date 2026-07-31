package ru.yandex.practicum;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.exception.StatisticsLoadException;
import ru.yandex.practicum.exception.StatisticsSaveException;
import ru.yandex.practicum.model.PlayerStats;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * этот класс должен выполнять рутинные процедуры по хранению результатов игроков
 */
public class WordleServerStatisticLoader {

    private static final Gson gson;
    private static final String STATISTICS_PATH = "statistics.json";

    static {
        gson = new GsonBuilder().create();
    }

    public static Map<String, PlayerStats> loadStatistics() {
        File file = new File(STATISTICS_PATH);

        if (!file.exists()) {
            return new HashMap<>();
        }
        StringBuilder stringBuilderMap = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(STATISTICS_PATH, StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilderMap.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new StatisticsLoadException("Ошибка при загрузки статистики",e);
        }

        Type playerStatsMapType = new TypeToken<Map<String, PlayerStats>>(){}.getType();
        return gson.fromJson(stringBuilderMap.toString(), playerStatsMapType);
    }

    public static void saveStatistics(Map<String, PlayerStats> playerStatsMap) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(STATISTICS_PATH, StandardCharsets.UTF_8))) {

            String json = gson.toJson(playerStatsMap);
            bufferedWriter.write(json);

        } catch (IOException e) {
            throw new StatisticsSaveException("Ошибка при сохранение статистики",e);
        }
    }
}
