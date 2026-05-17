package com.hospital.hospital.controller;

import com.hospital.hospital.model.*;
import com.hospital.hospital.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired private AdminService adminService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> data) {
        return adminService.login(data);
    }

    @PostMapping("/doctor/add")
    public Map<String, Object> addDoctor(@RequestBody Map<String, String> data) {
        return adminService.addDoctor(data);
    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        return adminService.getAllDoctors();
    }

    @DeleteMapping("/doctor/{id}")
    public Map<String, Object> removeDoctor(@PathVariable Long id) {
        return adminService.removeDoctor(id);
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return adminService.getOverviewStats();
    }

    @GetMapping("/appointments/today")
    public List<Appointment> getTodayAppointments() {
        return adminService.getTodayAppointments();
    }
}
