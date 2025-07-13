package com.tw.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PassengerDto {
    @NotBlank(message = "Aadhaar is required")
    @Size(min = 12, max = 12, message = "Aadhaar number must be exactly 12 digits")
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must contain only digits")
    private String aadhar;
    @NotBlank(message = "Name is required")
    private String name;
    @Min(value = 0, message = "Age must be positive")
    private int age;
    private String gender;
}
