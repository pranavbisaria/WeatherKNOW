package com.weatherknow.Service;

import com.weatherknow.Payload.SubscriptionModel;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    ResponseEntity<?> subscribe(SubscriptionModel subscriptionModel);

    String unsubscribe(String email);
}
