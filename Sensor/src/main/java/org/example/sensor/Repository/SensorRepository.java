package org.example.sensor.Repository;

import org.example.sensor.Entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Sensor findByName(String name);
}