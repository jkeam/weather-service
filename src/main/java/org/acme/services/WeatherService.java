package org.acme.services;

import org.acme.models.Weather;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;

public class WeatherService {
    private static String API_URL =  "https://api.openweathermap.org/data/2.5/weather?zip=%s&appid=%s&units=imperial";
    private static String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static String CONTENT_TYPE_VALUE = "application/json";
    private static String USER_AGENT_HEADER_NAME = "User-Agent";
    private static String USER_AGENT_VALUE = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36";

    public static Weather findWeather(String apiToken, String zip) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format(API_URL, zip, apiToken)))
            .timeout(Duration.ofMinutes(1))
            .header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_VALUE)
            .header(USER_AGENT_HEADER_NAME, USER_AGENT_VALUE)
            .build();

        try {
            HttpResponse<String> resp = client.send(request, BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = (Map<String, Object>)mapper.readValue(resp.body(), Map.class);

            String successCode = ((String)map.get("cod").toString());
            if (!successCode.equals("200")) {
                return new Weather(zip);
            }

            Map<String, Object> main = (Map<String, Object>)map.get("main");
            // {temp=89.26, feels_like=88.79, temp_min=87.01, temp_max=91, pressure=1012, humidity=59}
            return new Weather(zip, main.get("temp").toString());
        } catch(Exception e) {
            System.err.println(e);
            return new Weather(zip);
        }
    }
}