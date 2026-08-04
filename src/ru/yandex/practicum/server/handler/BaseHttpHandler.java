package ru.yandex.practicum.server.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.exception.InvalidQueryParameterException;
import ru.yandex.practicum.server.WordleStatistics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BaseHttpHandler {
    private final static int MAX_STEPS = 6;

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
        if (query != null && !query.isEmpty()) {
            String[] paramsArray = query.split("&");
            for (int i = 0; i < paramsArray.length; i++) {
                String[] values = paramsArray[i].split("=");
                if (values[0].equals("steps")) {
                    params.put(values[0], values[1]);
                }
                if (values[0].equals("usedhints")) {
                    params.put(values[0], values[1]);
                }
            }
        }
        return params;
    }

    protected boolean parseUsedHints(String usedHints) {
        if (usedHints == null) {
            throw new InvalidQueryParameterException("Передан пустой параметр 'usedHints'");
        }

        String usedHintsLowerCase = usedHints.trim().toLowerCase(Locale.ROOT);
        boolean usedHintsBoolean;

        switch (usedHintsLowerCase) {
            case "true": {
                usedHintsBoolean = true;
                break;
            }
            case "false": {
                usedHintsBoolean = false;
                break;
            }
            default: {
                throw new InvalidQueryParameterException("Передан некорректный параметр 'usedHints'");
            }
        }

        return usedHintsBoolean;
    }

    protected int parseSteps(String steps) {
        int stepsInteger;
        if (steps == null) {
            throw new InvalidQueryParameterException("Передан пустой параметр 'steps'");
        }

        try {
            stepsInteger = Integer.parseInt(steps);

            if (!(stepsInteger >= 0 && stepsInteger <= MAX_STEPS)) {
                throw new InvalidQueryParameterException("Передан некорректный параметр 'steps'");
            }

        } catch (NumberFormatException e) {
            throw new InvalidQueryParameterException("Передан некорректный параметр 'steps'");
        }

        return stepsInteger;
    }

    protected void sendBadRequest(HttpExchange h) throws IOException {
        h.sendResponseHeaders(HttpStatusCode.BAD_REQUEST.getCode(), -1);
        h.close();
    }

    protected void sendNotFound(HttpExchange h) throws IOException {
        h.sendResponseHeaders(HttpStatusCode.NOT_FOUND.getCode(), -1);
        h.close();
    }

    protected void sendInternalError(HttpExchange h) throws IOException {
        h.sendResponseHeaders(HttpStatusCode.INTERNAL_ERROR.getCode(), -1);
        h.close();
    }
}



