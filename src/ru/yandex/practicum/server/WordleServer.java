package ru.yandex.practicum.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.server.handler.WordleStatisticsHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * этот класс должен запускаться отдельно и внутри себя запускать веб-сервер для получения и обработки статистики от игровых клиентов
 */
public class WordleServer {
    private static final int PORT = 8080;
    private static final Gson gson;
    private static final WordleStatistics wordleStatistics;

    static {
        gson = new Gson();
        wordleStatistics = new WordleStatistics(WordleServerStatisticLoader.loadStatistics());
    }

    public static void main(String[] args) {
        WordleServer wordleServer = new WordleServer();
        wordleServer.serverStart();
    }

    private void serverStart() {
        try {
            HttpServer httpServer = HttpServer.create();

            httpServer.bind(new InetSocketAddress(PORT), 0);

            httpServer.createContext("/statistics", new WordleStatisticsHandler(wordleStatistics, gson));

            httpServer.start();
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
