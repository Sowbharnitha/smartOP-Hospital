package com.hospital.hospital.repository;

import com.hospital.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByMobile(String mobile);
    boolean existsByMobile(String mobile);
}
