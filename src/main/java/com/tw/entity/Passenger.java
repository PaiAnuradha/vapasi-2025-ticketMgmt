package com.tw.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tw.util.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

;import java.util.List;

@Data
@Entity
@Table(name="passenger")
public class Passenger {
    @Id
    @Size(min = 12, max = 12, message = "Aadhaar number must be exactly 12 digits")
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must contain only digits")
    private String aadhar;

    @Column(length = 30, nullable = false)
    private String name;

    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 150, message = "Age cannot be more than 150")
    @Column(nullable = false)
    private int age;

    @Column(length = 25)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name="pnr")
    private Ticket ticket;
}
