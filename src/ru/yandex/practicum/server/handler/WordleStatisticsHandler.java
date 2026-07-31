package ru.yandex.practicum.server.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.WordleStatistics;
import ru.yandex.practicum.exception.InvalidQueryParameterException;
import ru.yandex.practicum.model.PlayerStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordleStatisticsHandler extends BaseHttpHandler implements HttpHandler {

    private final WordleStatistics wordleStatistics;
    private final Gson gson;

    public WordleStatisticsHandler(WordleStatistics wordleStatistics, Gson gson) {
        this.wordleStatistics = wordleStatistics;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();

        try {
            if (method.equals("GET") && path.matches("^/statistics/[a-zA-Z0-9._-]{1,50}$")) {
                getStatistics(exchange, path);
            } else if (method.equals("POST") && path.matches("^/statistics/[a-zA-Z0-9._-]{1,50}$")) {
                addResult(exchange,path,query);
            }

        } catch (InvalidQueryParameterException e) {
            sendBadRequest(exchange);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addResult(HttpExchange exchange, String path, String query) throws IOException {
        String name = extractName(path);
        Map<String, String> stepsAndUsedHints = extractStepsAndUsedHints(query);

        if (stepsAndUsedHints == null || stepsAndUsedHints.isEmpty()) {
            throw new InvalidQueryParameterException("Переданы некорректные параметры steps или usedhints");
        }

        if (!(stepsAndUsedHints.get("steps") == null || stepsAndUsedHints.get("steps").matches("//d"))) {
            throw new InvalidQueryParameterException("Переданы некорректные параметры steps или usedhints");
        }

        wordleStatistics.addResult(name, steps, UsedHints);
        sendText(exchange, String.format("Результат игрока %s успешно добавлен", name), HttpStatusCode.OK.getCode());
    }

    private void getStatistics(HttpExchange exchange,String path) throws IOException {
        String name = extractName(path);

        PlayerStats playerStats = wordleStatistics.getPlayerStats(name);
        List<WordleStatistics.LeaderboardEntry> leaderboard = new ArrayList<>(wordleStatistics.getLeaderboard());

        if (playerStats != null) {
            leaderboard.add(new WordleStatistics.LeaderboardEntry(playerStats.nickname(), playerStats.games().size()));
        }

        String json = gson.toJson(leaderboard);
        sendText(exchange, json, HttpStatusCode.OK.getCode());
    }
}
