package com.hospital.hospital.repository;

import com.hospital.hospital.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorIdAndDate(String doctorId, LocalDate date);
    List<Appointment> findByDepartmentAndDate(String department, LocalDate date);
    List<Appointment> findByDate(LocalDate date);
}