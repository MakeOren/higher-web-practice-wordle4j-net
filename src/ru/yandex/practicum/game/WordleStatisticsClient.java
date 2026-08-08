package ru.yandex.practicum.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.exception.ApiException;
import ru.yandex.practicum.exception.NicknameNullException;
import ru.yandex.practicum.exception.StatisticsSubmissionFailedException;
import ru.yandex.practicum.game.model.LeaderboardEntry;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class WordleStatisticsClient {
    private final HttpClient httpClient;
    private final Gson gson;
    private static final String ADDRESS = "http://localhost:8080/statistics";

    public WordleStatisticsClient() {
        this.gson = new GsonBuilder().create();
        this.httpClient = HttpClient.newHttpClient();
    }

    public static void main(String[] args) {
        WordleStatisticsClient wordleStatisticsClient = new WordleStatisticsClient();
        List<LeaderboardEntry> g = new ArrayList<>(wordleStatisticsClient.getStatistics("ruslan3"));
        for (LeaderboardEntry leaderboardEntry : g) {
            System.out.println(leaderboardEntry.getNickname());
        }

        wordleStatisticsClient.sendResult("Ruslan", 4, true);

    }

    public void sendResult(String nickname, int steps, boolean usedHints) {
        HttpResponse<String> response;

        if (nickname == null) {
            throw new NicknameNullException("В метод WordleStatisticsClient.sendResult передано 'null' значение");
        }

        try {
            String requestString = String.format("%s/%s?steps=%d&usedhints=%b",ADDRESS, nickname ,steps, usedHints);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestString))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new StatisticsSubmissionFailedException("Не удалось отправить статистику на сервер");
        }

        checkStatus(response);
    }

    public List<LeaderboardEntry> getStatistics(String nickname) {
        HttpResponse<String> response;

        if (nickname == null) {
            throw new NicknameNullException("В метод WordleStatisticsClient.getStatistics передано 'null' значение");
        }

        try {
            String requestString = String.format("%s/%s",ADDRESS, nickname);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestString))
                    .GET()
                    .build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new StatisticsSubmissionFailedException("Не удалось получить статистику от сервера");
        }

        checkStatus(response);
        return parseStatistics(response);
    }

    private List<LeaderboardEntry> parseStatistics(HttpResponse<String> response) {
        String json = response.body();
        Type leaderBoardListType = new TypeToken<List<LeaderboardEntry>>(){}.getType();

        return gson.fromJson(json, leaderBoardListType);
    }

    private void checkStatus(HttpResponse<String> response) {
        if (!(response.statusCode() >= 200 && response.statusCode() < 300)) {
            throw new ApiException("Получен отрицательный статус: " + response.statusCode());
        }
    }
}
