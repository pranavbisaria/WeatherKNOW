package com.weatherknow.Service.Impl;

import com.weatherknow.Payload.SubscriptionModel;
import com.weatherknow.Service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    public ResponseEntity<?> subscribe(SubscriptionModel subscriptionModel){
        subscriptionModel.Cities().forEach((city)->{
            city.toLowerCase().trim();


        });
    }
}
