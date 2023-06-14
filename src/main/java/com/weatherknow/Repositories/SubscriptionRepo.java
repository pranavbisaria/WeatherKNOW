package com.weatherknow.Repositories;

import com.weatherknow.Models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, String> {
    Optional<Subscription> findByEmail(String email);
}
