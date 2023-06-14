package com.weatherknow.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cities {
    @Id
    private String name;
    private String tz_id;
    private LocalDateTime last_updated;
    private float temp_c;
    private float temp_f;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Condition> condition = new ArrayList<>(0);
    private float humidity;
    private float cloud;
}
