package com.hospital.hospital.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private String patientName;
    private String hospital;
    private String department;
    private String doctorName;
    private String doctorId;
    private LocalDate date;
    private String timeSlot;
    private String reason;
    private String tokenNumber;
    private int tokenNum;
    private String status;
}
