package org.example.sensor.Controllertest;

import org.example.sensor.Controller.MeasurementController;
import org.example.sensor.Dto.MeasurementDTO;
import org.example.sensor.Entity.Measurement;
import org.example.sensor.Service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MeasurementControllerTest {

    @InjectMocks
    private MeasurementController measurementController;

    @Mock
    private MeasurementService measurementService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddMeasurement() {
        MeasurementDTO dto = new MeasurementDTO(23.5, true);
        Measurement mockMeasurement = new Measurement();
        mockMeasurement.setValue(23.5);

        when(measurementService.addMeasurement(dto)).thenReturn(mockMeasurement);

        Measurement measurement = measurementController.addMeasurement(dto);
        assertNotNull(measurement);
        assertEquals(23.5, measurement.getValue());
        verify(measurementService).addMeasurement(dto);
    }

    @Test
    public void testGetAllMeasurements() {
        List<Measurement> mockMeasurements = new ArrayList<>();
        mockMeasurements.add(new Measurement());
        when(measurementService.getAllMeasurements()).thenReturn(mockMeasurements);

        List<Measurement> measurements = measurementController.getAllMeasurements();
        assertEquals(1, measurements.size());
        verify(measurementService).getAllMeasurements();
    }

    @Test
    public void testCountRainyDays() {
        when(measurementService.countRainyDays()).thenReturn(5L);
        long count = measurementController.countRainyDays();
        assertEquals(5L, count);
        verify(measurementService).countRainyDays();
    }
}