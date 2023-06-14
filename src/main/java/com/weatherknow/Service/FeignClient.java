package com.weatherknow.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@org.springframework.cloud.openfeign.FeignClient(name = "youtube-api", url = "http://api.weatherapi.com/v1")
public interface FeignClient {
    @GetMapping("/current.json")
    ResponseEntity<?> getLatestWeather(@RequestParam String key, @RequestParam String q);
}
