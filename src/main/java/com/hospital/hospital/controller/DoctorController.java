package com.hospital.hospital.controller;

import com.hospital.hospital.model.Appointment;
import com.hospital.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired private DoctorService doctorService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> data) {
        return doctorService.login(data);
    }

    @GetMapping("/queue/{doctorId}")
    public List<Appointment> getQueue(@PathVariable String doctorId) {
        return doctorService.getTodayQueue(doctorId);
    }

    @PutMapping("/status/{appointmentId}")
    public Map<String, Object> markStatus(
            @PathVariable Long appointmentId,
            @RequestParam String status) {
        return doctorService.markStatus(appointmentId, status);
    }

    @PutMapping("/callnext/{doctorId}")
    public Map<String, Object> callNext(@PathVariable String doctorId) {
        return doctorService.callNext(doctorId);
    }
}