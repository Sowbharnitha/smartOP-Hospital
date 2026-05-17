package com.hospital.hospital.service;

import com.hospital.hospital.model.*;
import com.hospital.hospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class DoctorService {

    @Autowired private DoctorRepository doctorRepo;
    @Autowired private AppointmentRepository appointmentRepo;

    public Map<String, Object> login(Map<String, String> data) {
        Map<String, Object> res = new HashMap<>();
        Optional<Doctor> opt = doctorRepo.findByDoctorId(data.get("doctorId"));
        if (opt.isEmpty() || !opt.get().getPassword().equals(data.get("password"))) {
            res.put("success", false);
            res.put("message", "Invalid Doctor ID or password");
            return res;
        }
        Doctor d = opt.get();
        res.put("success", true);
        res.put("doctorId", d.getDoctorId());
        res.put("name", d.getName());
        res.put("department", d.getDepartment());
        return res;
    }

    public List<Appointment> getTodayQueue(String doctorId) {
        return appointmentRepo.findByDoctorIdAndDate(doctorId, LocalDate.now());
    }

    public Map<String, Object> markStatus(Long appointmentId, String status) {
        Map<String, Object> res = new HashMap<>();
        Optional<Appointment> opt = appointmentRepo.findById(appointmentId);
        if (opt.isEmpty()) { res.put("success", false); return res; }
        Appointment a = opt.get();
        if (status.equals("consulting")) {
            List<Appointment> queue = appointmentRepo.findByDoctorIdAndDate(a.getDoctorId(), LocalDate.now());
            for (Appointment q : queue) {
                if (q.getStatus().equals("consulting")) { q.setStatus("waiting"); appointmentRepo.save(q); }
            }
        }
        a.setStatus(status);
        appointmentRepo.save(a);
        res.put("success", true);
        return res;
    }

    public Map<String, Object> callNext(String doctorId) {
        Map<String, Object> res = new HashMap<>();
        List<Appointment> queue = appointmentRepo.findByDoctorIdAndDate(doctorId, LocalDate.now());
        for (Appointment a : queue) {
            if (a.getStatus().equals("consulting")) { a.setStatus("done"); appointmentRepo.save(a); break; }
        }
        for (Appointment a : queue) {
            if (a.getStatus().equals("waiting")) {
                a.setStatus("consulting"); appointmentRepo.save(a);
                res.put("success", true);
                res.put("calledToken", a.getTokenNumber());
                res.put("calledName", a.getPatientName());
                return res;
            }
        }
        res.put("success", false);
        res.put("message", "No more patients waiting");
        return res;
    }
}

