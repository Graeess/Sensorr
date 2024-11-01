package org.example.sensor.Controllertest;
import org.example.sensor.Controller.SensorController;
import org.example.sensor.Dto.SensorRegistrationDTO;
import org.example.sensor.Entity.Sensor;
import org.example.sensor.Service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SensorControllerTest {

    @InjectMocks
    private SensorController sensorController;

    @Mock
    private SensorService sensorService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSensor() {
        SensorRegistrationDTO dto = new SensorRegistrationDTO("sensorName");
        Sensor mockSensor = new Sensor();
        mockSensor.setName("sensorName");

        when(sensorService.registerSensor(dto)).thenReturn(mockSensor);

        Sensor sensor = sensorController.registerSensor(dto);
        assertNotNull(sensor);
        assertEquals("sensorName", sensor.getName());
        verify(sensorService).registerSensor(dto);
    }
}