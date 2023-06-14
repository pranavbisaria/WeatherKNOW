package com.weatherknow.Controlller;

import com.weatherknow.Payload.SubscriptionModel;
import com.weatherknow.Service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    ResponseEntity<?> subscribeNotification(@RequestBody SubscriptionModel subscriptionModel){
        return this.subscriptionService.subscribe(subscriptionModel);
    }
}
