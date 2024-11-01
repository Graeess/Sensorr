package org.example.sensor.ServiceTest;

import org.example.sensor.Dto.SensorRegistrationDTO;
import org.example.sensor.Entity.Sensor;
import org.example.sensor.Repository.SensorRepository;
import org.example.sensor.Service.SensorService; // Убедитесь, что это правильный импорт
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SensorServiceTest {

    @InjectMocks
    private SensorService sensorService;

    @Mock
    private SensorRepository sensorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSensor() {
        SensorRegistrationDTO dto = new SensorRegistrationDTO("sensorName");

        // Создание объекта Sensor, который будет возвращен из mock-репозитория
        Sensor sensor = new Sensor();
        sensor.setName(dto.getName());

        // Настройка mock-репозитория
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        // Вызов метода регистрации
        Sensor result = sensorService.registerSensor(dto);

        // Проверка результатов
        assertNotNull(result, "Sensor should not be null");
        assertEquals(dto.getName(), result.getName());
        verify(sensorRepository).save(any(Sensor.class));
    }
}
