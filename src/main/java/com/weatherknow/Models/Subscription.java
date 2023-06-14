package com.weatherknow.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    private String email;
    private String name;
    @ManyToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private List<Cities> citiesList= new ArrayList<>(0);
}
