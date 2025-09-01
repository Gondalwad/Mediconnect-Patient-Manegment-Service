package com.PatientService.Service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Email is Required")
    @Column(unique = true)
    @Email
    private String email;

    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private LocalDate dob;
    @NotNull
    private LocalDate registration_date;

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getAddress() {
        return address;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    public @NotNull LocalDate getDob() {
        return dob;
    }

    public void setDob(@NotNull LocalDate dob) {
        this.dob = dob;
    }

    public @NotNull LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(@NotNull LocalDate registration_date) {
        this.registration_date = registration_date;
    }
}
