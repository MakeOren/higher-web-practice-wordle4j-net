package ru.yandex.practicum.server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BaseHttpHandler {

    protected void sendText(HttpExchange h, String text, int code) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(code, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected String readBody(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    protected String extractName(String path) {
        String[] parts = path.split("/");
        return parts[parts.length - 1];
    }

    protected Map<String, String> extractStepsAndUsedHints(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null || query.isEmpty()) {
            String[] paramsArray = query.split("&");
            if (paramsArray.length == 2) {
                for (int i = 0; i < paramsArray.length; i++) {
                    String[] values = paramsArray[i].split("=");
                    if (values[0].equals("steps") && i == 0) {
                        params.put(values[0], values[1]);
                    }
                    if (values[0].equals("usedhints") && i == 1) {
                        params.put(values[0], values[1]);
                    }
                }
            }
        }
        return params;
    }
}



