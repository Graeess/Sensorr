package org.example.sensor.Service;

import org.example.sensor.Dto.MeasurementDTO;
import org.example.sensor.Entity.Measurement;
import org.example.sensor.Entity.Sensor;
import org.example.sensor.Repository.MeasurementRepository;
import org.example.sensor.Repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public Measurement addMeasurement(MeasurementDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("MeasurementDTO cannot be null");
        }

        if (dto.getSensor() == null) {
            throw new IllegalArgumentException("Sensor information is missing");
        }

        Sensor sensor = sensorRepository.findByName(dto.getSensor());
        if (sensor == null) {
            throw new RuntimeException("Sensor not found");
        }

        Measurement measurement = new Measurement();
        measurement.setValue(dto.getValue());
        measurement.setRaining(dto.isRaining());
        measurement.setSensor(sensor);
        return measurementRepository.save(measurement);
    }
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    public long countRainyDays() {
        return measurementRepository.findAll().stream()
                .filter(Measurement::isRaining)
                .count();
    }
}
