package org.example.sensor.Service;

import org.example.sensor.Dto.SensorRegistrationDTO;
import org.example.sensor.Entity.Sensor;
import org.example.sensor.Repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    public Sensor registerSensor(SensorRegistrationDTO dto) {
        if (sensorRepository.findByName(dto.getName()) != null) {
            throw new RuntimeException("Sensor with this name already exists");
        }
        Sensor sensor = new Sensor();
        sensor.setName(dto.getName());
        return sensorRepository.save(sensor);
    }
}