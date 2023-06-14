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
    private Double temp_c;
    private Double temp_f;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Condition condition;
    private Double humidity;
    private Double cloud;
}
