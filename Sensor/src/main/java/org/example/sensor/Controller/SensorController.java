package org.example.sensor.Controller;


import org.example.sensor.Dto.SensorRegistrationDTO;
import org.example.sensor.Entity.Sensor;
import org.example.sensor.Service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("/sensors")
    public class SensorController {
        @Autowired
        private SensorService sensorService;

        @PostMapping("/registration")
        public Sensor registerSensor(@RequestBody SensorRegistrationDTO dto) {
            return sensorService.registerSensor(dto);
        }
    }

