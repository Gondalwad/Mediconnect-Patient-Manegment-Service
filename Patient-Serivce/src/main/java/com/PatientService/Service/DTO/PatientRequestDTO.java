package com.PatientService.Service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name not allowed")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date os birth is required")
    private String dob;


    // Getter & Setters

    // Getter - name
    public @NotBlank(message = "Name is required") @Size(min = 1, max = 100, message = "Name not allowed") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") @Size(min = 1, max = 100, message = "Name not allowed") String name) {
        this.name = name;
    }

    // Getter - email
    public @NotBlank(message = "Email is required") @Email(message = "Invalid Email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid Email") String email) {
        this.email = email;
    }

    // Getter - address
    public @NotBlank(message = "Address is required") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address is required") String address) {
        this.address = address;
    }

    // Getter - dob
    public @NotBlank(message = "Date os birth is required") String getDob() {
        return dob;
    }

    public void setDob(@NotBlank(message = "Date os birth is required") String dob) {
        this.dob = dob;
    }

}
