package com.weatherknow.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherknow.Models.Cities;
import com.weatherknow.Models.Condition;
import com.weatherknow.Models.Subscription;
import com.weatherknow.Payload.ApiResponse;
import com.weatherknow.Payload.SubscriptionModel;
import com.weatherknow.Repositories.CitiesRepo;
import com.weatherknow.Repositories.SubscriptionRepo;
import com.weatherknow.Service.FeignClient;
import com.weatherknow.Service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    @Value("${key}")
    private String key;
    private final FeignClient feignClient;
    private final CitiesRepo citiesRepo;
    private final SubscriptionRepo subscriptionRepo;
    @Override
    public ResponseEntity<?> subscribe(SubscriptionModel subscriptionModel){
        List<String> citiesNotFound = new ArrayList<>(0);
        List<Cities> citiesFound = new ArrayList<>(0);

        subscriptionModel.Cities().forEach((city)->{
            city.toLowerCase().trim();
            Optional<Cities> cities = this.citiesRepo.findById(city);
            if(cities.isEmpty()) {
                ResponseEntity<?> response = this.feignClient.getLatestWeather(key, "region:"+city+",country:india");
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode;
                try {
                    jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(response.getBody()));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                JsonNode locationNode = jsonNode.get("location");
                if(!city.equals(locationNode.get("name").asText().toLowerCase())){
                    citiesNotFound.add(city);
                }
                else{
                    JsonNode current = jsonNode.get("current");
                    JsonNode condition = current.get("condition");

                    String lastUpdated = current.get("last_updated").asText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime lastUpdatedDateTime = LocalDateTime.parse(lastUpdated, formatter);

                    Cities newCities = new Cities(
                            locationNode.get("name").asText(),
                            locationNode.get("tz_id").asText(),
                            lastUpdatedDateTime,
                            current.get("temp_c").asDouble(),
                            current.get("temp_f").asDouble(),
                            new Condition(condition.get("code").asText(),condition.get("text").asText(), condition.get("icon").asText()),
                            current.get("humidity").asDouble(),
                            current.get("cloud").asDouble()
                    );
                    this.citiesRepo.save(newCities);
                    citiesFound.add(newCities);
                }
            } else {
                citiesFound.add(cities.get());
            }
        });
        Subscription subscription = new Subscription();
        subscription.setName(subscriptionModel.name().trim());
        subscription.setEmail(subscriptionModel.email().toLowerCase().trim());
        subscription.getCitiesList().addAll(citiesFound);
        this.subscriptionRepo.save(subscription);

        AtomicReference<String> message = new AtomicReference<>("Successfully subscribed!");
        if(!citiesNotFound.isEmpty()){
            citiesNotFound.forEach((city)->{
                message.set(message + ", " + city);
            });
            message.set(message + " are not found!!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(message.get(), true));
    }
}
