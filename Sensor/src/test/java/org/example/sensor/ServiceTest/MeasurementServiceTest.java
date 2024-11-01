package org.example.sensor.ServiceTest;

import org.example.sensor.Dto.MeasurementDTO;
import org.example.sensor.Dto.SensorDTO;
import org.example.sensor.Entity.Measurement;
import org.example.sensor.Entity.Sensor;
import org.example.sensor.Repository.MeasurementRepository;
import org.example.sensor.Repository.SensorRepository;
import org.example.sensor.Service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MeasurementServiceTest {

    @InjectMocks
    private MeasurementService measurementService;

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private SensorRepository sensorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMeasurement_SensorNotFound() {
        MeasurementDTO dto = new MeasurementDTO();
        dto.setValue(25.0);
        dto.setRaining(false);
        dto.setSensor("Test sensor");

        when(sensorRepository.findByName(dto.getSensor())).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            measurementService.addMeasurement(dto);
        });

        assertEquals("Sensor not found", exception.getMessage());
    }

    @Test
    void testAddMeasurement_Success() {
        MeasurementDTO dto = new MeasurementDTO();
        dto.setValue(25.0);
        dto.setRaining(false);
        dto.setSensor("Test sensor");

        Sensor sensor = new Sensor();
        sensor.setName("Test sensor");
        sensor.setId(123L);

        when(sensorRepository.findByName(dto.getSensor())).thenReturn(sensor);
        when(measurementRepository.save(any(Measurement.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Measurement result = measurementService.addMeasurement(dto);

        assertNotNull(result);
        assertEquals(25.0, result.getValue());
        assertFalse(result.isRaining());
        assertEquals(dto.getSensor(), result.getSensor().getName());
    }
}
