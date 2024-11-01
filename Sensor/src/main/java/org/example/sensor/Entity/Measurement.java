package org.example.sensor.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value;
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;
}