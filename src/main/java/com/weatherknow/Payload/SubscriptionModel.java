package com.weatherknow.Payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
public record SubscriptionModel(@NotBlank(message = "Name should not be left empty") String name, @NotNull(message = "Please, select the cities") List<String> cities, @Email(message = "Not a valid email") @NotBlank(message = "Email can't be blank") String email) {}
