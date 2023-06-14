package com.weatherknow.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherknow.Payload.SubscriptionModel;
import com.weatherknow.Service.FeignClient;
import com.weatherknow.Service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    @Value("${key}")
    private String key;
    private final FeignClient feignClient;
    public ResponseEntity<?> subscribe(SubscriptionModel subscriptionModel){
        List<String> citiesNotFound = new ArrayList<>(0);

        subscriptionModel.Cities().forEach((city)->{
            city.toLowerCase().trim();
            this.
            ResponseEntity<?> response = this.feignClient.getLatestWeather(key, "region:"+city);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode;
            try {
                jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(response.getBody()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            JsonNode locationNode = jsonNode.get("location");
            String cityName = locationNode.get("name").asText();
            String region = locationNode.get("region").asText();
            String country = locationNode.get("country").asText();
            double latitude = locationNode.get("lat").asDouble();
            double longitude = locationNode.get("lon").asDouble();

            JsonNode current = jsonNode.get("current");
            double tempC = current.get("temp_c").asDouble();
            double tempF = current.get("temp_f").asDouble();
            boolean isDay = current.get("is_day").asBoolean();

            JsonNode condition = current.get("condition");
            String conditionText = condition.get("text").asText();
            String conditionIcon = condition.get("icon").asText();
            int conditionCode = condition.get("code").asInt();
        });
    }
}
