package org.example.sensor.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorRegistrationDTO {
    private String name;

    // Конструктор с параметрами
    public SensorRegistrationDTO(String sensorName) {
        this.name = name;
    }

}