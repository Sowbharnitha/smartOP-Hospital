package com.hospital.hospital.service;

import com.hospital.hospital.model.*;
import com.hospital.hospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class AdminService {

    @Autowired private DoctorRepository doctorRepo;
    @Autowired private PatientRepository patientRepo;
    @Autowired private AppointmentRepository appointmentRepo;

    public Map<String, Object> login(Map<String, String> data) {
        Map<String, Object> res = new HashMap<>();
        if (data.get("adminId").equals("ADMIN001") && data.get("password").equals("admin123")) {
            res.put("success", true);
            res.put("name", "Admin");
        } else {
            res.put("success", false);
            res.put("message", "Invalid admin credentials");
        }
        return res;
    }

    public Map<String, Object> addDoctor(Map<String, String> data) {
        Map<String, Object> res = new HashMap<>();
        if (doctorRepo.existsByDoctorId(data.get("doctorId"))) {
            res.put("success", false);
            res.put("message", "Doctor ID already exists");
            return res;
        }
        Doctor d = new Doctor();
        d.setName(data.get("name"));
        d.setDoctorId(data.get("doctorId"));
        d.setPassword(data.get("password"));
        d.setDepartment(data.get("department"));
        d.setQualification(data.get("qualification"));
        d.setSlotsPerDay(Integer.parseInt(data.get("slotsPerDay")));
        d.setStatus("active");
        doctorRepo.save(d);
        res.put("success", true);
        return res;
    }

    public List<Doctor> getAllDoctors() { return doctorRepo.findAll(); }

    public Map<String, Object> removeDoctor(Long id) {
        doctorRepo.deleteById(id);
        return Map.of("success", true);
    }

    public Map<String, Object> getOverviewStats() {
        Map<String, Object> res = new HashMap<>();
        List<Appointment> todayAppts = appointmentRepo.findByDate(LocalDate.now());
        res.put("totalToday", todayAppts.size());
        res.put("done", todayAppts.stream().filter(a -> a.getStatus().equals("done")).count());
        res.put("waiting", todayAppts.stream().filter(a -> a.getStatus().equals("waiting")).count());
        res.put("noshow", todayAppts.stream().filter(a -> a.getStatus().equals("noshow")).count());
        res.put("activeDoctors", doctorRepo.findAll().stream().filter(d -> d.getStatus().equals("active")).count());
        res.put("totalPatients", patientRepo.count());
        return res;
    }

    public List<Appointment> getTodayAppointments() {
        return appointmentRepo.findByDate(LocalDate.now());
    }
}
