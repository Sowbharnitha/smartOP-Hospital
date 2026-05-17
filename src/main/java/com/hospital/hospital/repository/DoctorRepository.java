package com.hospital.hospital.repository;

import com.hospital.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByDoctorId(String doctorId);
    boolean existsByDoctorId(String doctorId);
}
