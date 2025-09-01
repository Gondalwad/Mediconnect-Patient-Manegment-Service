package com.PatientService.Service.controller;

import com.PatientService.Service.DTO.PatientDTO;
import com.PatientService.Service.DTO.PatientRequestDTO;
import com.PatientService.Service.exception.EmailExistsException;
import com.PatientService.Service.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient")
public class PatientController{


    PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients(){

        List<PatientDTO> patients = patientService.getAllPatient();
        return ResponseEntity.ok().body(patients);
    }

//    @GetMapping("all-patients/{id}")
//    public ResponseEntity<PatientDTO> getPatient(@PathVariable("id") UUID id){
//
//        PatientDTO dto = patientService.getPatient(id);
//        if (dto == null){
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok().body(dto);
//    }

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody @Valid PatientRequestDTO patientRequestDTO){
        PatientDTO patientDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.ok().body(patientDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable UUID id, @RequestBody PatientRequestDTO patientRequestDTO){

        return ResponseEntity.ok(patientService.updatePatient(id,patientRequestDTO));



    }

}
