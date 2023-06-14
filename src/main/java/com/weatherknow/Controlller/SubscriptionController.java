package com.weatherknow.Controlller;

import com.weatherknow.Payload.SubscriptionModel;
import com.weatherknow.Service.SubscriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    @PostMapping("/subscribe")
    ResponseEntity<?> subscribeNotification(@Valid @RequestBody SubscriptionModel subscriptionModel){
        return this.subscriptionService.subscribe(subscriptionModel);
    }

    @DeleteMapping("/unsubscribe")
    public String unsubscribeNotification(@Valid @Email(message = "Not a valid email") @RequestParam("email") String email){
        return this.subscriptionService.unsubscribe(email);
    }
}
