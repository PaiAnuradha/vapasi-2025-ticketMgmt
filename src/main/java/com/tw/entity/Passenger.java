package com.tw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

@Data
@Entity
@Table(name="passenger")
public class Passenger {
    @Id
    @Column(length = 12)


//    @Size(min = 12, max = 12, message = "Aadhaar number must be exactly 12 digits")
//    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must contain only digits")
    private String aadhar;

    @Column(length = 30)
    private String name;
    private int age;
    @Column(length = 25)
    private String gender;

}
