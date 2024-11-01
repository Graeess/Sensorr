package org.example.sensor.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
    private double value;
    private boolean raining;
    private SensorDTO sensor;

    public MeasurementDTO() {
        sensor = new SensorDTO();
    }


    public MeasurementDTO(double value, boolean raining) {
        this.value = value;
        this.raining = raining;
    }

    public String getSensor(){
        return sensor.getSensor();
    }

    public void setSensor(String name) {
        sensor.setSensor(name);
    }
}


