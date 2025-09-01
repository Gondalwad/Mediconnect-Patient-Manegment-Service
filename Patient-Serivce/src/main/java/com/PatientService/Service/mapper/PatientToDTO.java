package com.PatientService.Service.mapper;

import com.PatientService.Service.DTO.PatientDTO;
import com.PatientService.Service.DTO.PatientRequestDTO;
import com.PatientService.Service.model.Patient;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Objects;

public class PatientToDTO {

    public static PatientDTO toDTO(Patient patient){
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setDob(patient.getDob().toString());
        patientDTO.setEmail(patient.getEmail());

        return patientDTO;
    }

    public static Patient toPatient(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName()); // set name
        patient.setAddress(patientRequestDTO.getAddress()); // set address
        patient.setDob(LocalDate.parse(patientRequestDTO.getDob())); // set dob
        patient.setEmail(patientRequestDTO.getEmail()); // set email

        return patient;
    }
}
