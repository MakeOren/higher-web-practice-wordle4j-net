package ru.yandex.practicum.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpWordleServer {

    private static final int PORT = 8080;
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public static void main(String[] args) {

    }

    private void serverStart() {
        try {
            HttpServer httpServer = HttpServer.create();

            httpServer.bind(new InetSocketAddress(PORT), 0);

            httpServer.createContext();

            httpServer.start();
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
