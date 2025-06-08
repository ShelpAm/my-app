package com.mycompany.app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Weather：从 OpenWeatherMap 获取当前天气，并映射到 WeatherType
 */
public class WeatherService {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 获取指定城市的当前天气类型
     * 
     * @param cityName 城市名，比如 "Nanchang"
     * @return WeatherType 枚举
     * @throws IOException          网络或 JSON 解析错误
     * @throws InterruptedException 请求被中断
     */
    public WeatherType getCurrentWeather(String cityName) throws IOException, InterruptedException {
        // 构造请求 URL
        String url = String.format("%s?q=%s&appid=%s", API_URL, cityName, apiKey);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // 发送请求
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Failed to get weather: HTTP " + response.statusCode());
        }

        // 解析 JSON
        JsonNode root = objectMapper.readTree(response.body());
        JsonNode weatherArray = root.path("weather");
        if (!weatherArray.isArray() || weatherArray.size() == 0) {
            return WeatherType.UNKNOWN;
        }
        int code = weatherArray.get(0).path("id").asInt(-1);
        return WeatherType.fromCode(code);
    }
}
