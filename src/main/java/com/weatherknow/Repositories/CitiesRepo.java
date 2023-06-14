package com.weatherknow.Repositories;

import com.weatherknow.Models.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepo extends JpaRepository<Cities, String> {
}
