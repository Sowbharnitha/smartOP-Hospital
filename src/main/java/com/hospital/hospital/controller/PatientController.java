package com.hospital.hospital.controller;

import com.hospital.hospital.model.Appointment;
import com.hospital.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired private PatientService patientService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> data) {
        return patientService.register(data);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> data) {
        return patientService.login(data);
    }

    @PostMapping("/book")
    public Map<String, Object> book(@RequestBody Map<String, String> data) {
        return patientService.bookAppointment(data);
    }

    @GetMapping("/appointments/{patientId}")
    public List<Appointment> getAppointments(@PathVariable Long patientId) {
        return patientService.getMyAppointments(patientId);
    }

    @GetMapping("/queue/{appointmentId}")
    public Map<String, Object> getQueue(@PathVariable Long appointmentId) {
        return patientService.getQueuePosition(appointmentId);
    }

    @PutMapping("/cancel/{appointmentId}")
    public Map<String, Object> cancel(@PathVariable Long appointmentId) {
        return patientService.cancelAppointment(appointmentId);
    }
}
