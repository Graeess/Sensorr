package org.example.sensor.Controller;

import org.example.sensor.Dto.MeasurementDTO;
import org.example.sensor.Entity.Measurement;
import org.example.sensor.Service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    @Autowired
    private MeasurementService measurementService;

    @PostMapping("/add")
    public Measurement addMeasurement(@RequestBody MeasurementDTO dto) {
        return measurementService.addMeasurement(dto);
    }

    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementService.getAllMeasurements();
    }

    @GetMapping("/rainyDaysCount")
    public long countRainyDays() {
        return measurementService.countRainyDays();
    }
}