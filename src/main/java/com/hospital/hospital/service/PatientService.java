package com.hospital.hospital.service;

import com.hospital.hospital.model.*;
import com.hospital.hospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class PatientService {

    @Autowired private PatientRepository patientRepo;
    @Autowired private AppointmentRepository appointmentRepo;

    public Map<String, Object> register(Map<String, String> data) {
        Map<String, Object> res = new HashMap<>();
        if (patientRepo.existsByMobile(data.get("mobile"))) {
            res.put("success", false);
            res.put("message", "Mobile already registered");
            return res;
        }
        Patient p = new Patient();
        p.setName(data.get("name"));
        p.setMobile(data.get("mobile"));
        p.setPassword(data.get("password"));
        p.setAge(Integer.parseInt(data.get("age")));
        p.setGender(data.get("gender"));
        patientRepo.save(p);
        res.put("success", true);
        res.put("message", "Registered successfully");
        return res;
    }

    public Map<String, Object> login(Map<String, String> data) {
        Map<String, Object> res = new HashMap<>();
        Optional<Patient> opt = patientRepo.findByMobile(data.get("mobile"));
        if (opt.isEmpty() || !opt.get().getPassword().equals(data.get("password"))) {
            res.put("success", false);
            res.put("message", "Invalid mobile or password");
            return res;
        }
        Patient p = opt.get();
        res.put("success", true);
        res.put("patientId", p.getId());
        res.put("name", p.getName());
        res.put("mobile", p.getMobile());
        return res;
    }

    public Map<String, Object> bookAppointment(Map<String, String> data) {
        Map<String, Object> res = new HashMap<>();
        LocalDate date = LocalDate.parse(data.get("date"));
        List<Appointment> existing = appointmentRepo.findByDepartmentAndDate(data.get("department"), date);
        int tokenNum = existing.size() + 1;
        String tokenNumber = "OP-" + String.format("%03d", tokenNum);

        Appointment a = new Appointment();
        a.setPatientId(Long.parseLong(data.get("patientId")));
        a.setPatientName(data.get("patientName"));
        a.setHospital(data.get("hospital"));
        a.setDepartment(data.get("department"));
        a.setDoctorName(data.get("doctorName"));
        a.setDoctorId(data.get("doctorId"));
        a.setDate(date);
        a.setTimeSlot(data.get("timeSlot"));
        a.setReason(data.get("reason"));
        a.setTokenNumber(tokenNumber);
        a.setTokenNum(tokenNum);
        a.setStatus("waiting");
        appointmentRepo.save(a);

        res.put("success", true);
        res.put("tokenNumber", tokenNumber);
        res.put("tokenNum", tokenNum);
        res.put("appointmentId", a.getId());
        return res;
    }

    public List<Appointment> getMyAppointments(Long patientId) {
        return appointmentRepo.findByPatientId(patientId);
    }

    public Map<String, Object> getQueuePosition(Long appointmentId) {
        Map<String, Object> res = new HashMap<>();
        Optional<Appointment> opt = appointmentRepo.findById(appointmentId);
        if (opt.isEmpty()) { res.put("success", false); return res; }
        Appointment myAppt = opt.get();
        List<Appointment> todayQueue = appointmentRepo.findByDepartmentAndDate(myAppt.getDepartment(), myAppt.getDate());
        int currentServing = 0;
        for (Appointment a : todayQueue) {
            if (a.getStatus().equals("done") || a.getStatus().equals("noshow")) currentServing++;
        }
        int ahead = Math.max(0, myAppt.getTokenNum() - currentServing - 1);
        res.put("success", true);
        res.put("tokenNumber", myAppt.getTokenNumber());
        res.put("ahead", ahead);
        res.put("waitMins", ahead * 5);
        res.put("currentServing", currentServing);
        res.put("totalToday", todayQueue.size());
        res.put("status", myAppt.getStatus());
        res.put("alert", ahead <= 2 && ahead > 0);
        res.put("yourTurn", ahead == 0 && myAppt.getStatus().equals("consulting"));
        return res;
    }

    public Map<String, Object> cancelAppointment(Long appointmentId) {
        Map<String, Object> res = new HashMap<>();
        Optional<Appointment> opt = appointmentRepo.findById(appointmentId);
        if (opt.isPresent()) {
            Appointment a = opt.get();
            a.setStatus("cancelled");
            appointmentRepo.save(a);
            res.put("success", true);
        } else { res.put("success", false); }
        return res;
    }
}