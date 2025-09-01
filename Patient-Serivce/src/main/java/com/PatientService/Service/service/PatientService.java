package com.PatientService.Service.service;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.PatientService.Service.DTO.PatientDTO;
import com.PatientService.Service.DTO.PatientRequestDTO;
import com.PatientService.Service.exception.EmailExistsException;
import com.PatientService.Service.exception.PatientNotFoundException;
import com.PatientService.Service.grpc.BillingServiceGrpcClient;
import com.PatientService.Service.kafka.KafkaProducer;
import com.PatientService.Service.mapper.PatientToDTO;
import com.PatientService.Service.model.Patient;
import com.PatientService.Service.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {


    PatientRepository patientRepository;
    BillingServiceGrpcClient grpcClient;
    KafkaProducer kafkaProducer;
    @Autowired
    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient grpcClient,KafkaProducer kafkaProducer){
        this.patientRepository = patientRepository;
        this.grpcClient = grpcClient;
        this.kafkaProducer = kafkaProducer;
    }

//    ############### Gets all Patients
    public List<PatientDTO> getAllPatient(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientToDTO::toDTO).toList();
    }

    // ############ Get single patient using UUID
    public PatientDTO getPatient(UUID id) {
        Optional<Patient> p = patientRepository.findById(id);
        PatientDTO patientDTO = new PatientDTO();
        return p.map(PatientToDTO::toDTO).orElse(null);
    }

    // Handles business logic to create patient
    public PatientDTO createPatient(PatientRequestDTO patientRequestDTO) {
        // Check if email already Exists
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailExistsException("Email "+ patientRequestDTO.getEmail() + " is already registered");
        }
        // convert param dto to Patient
        Patient patient = PatientToDTO.toPatient(patientRequestDTO);
        // Need to save the registration Date
        patient.setRegistration_date(LocalDate.now());
        // save to db
        Patient recivedPatient = patientRepository.save(patient);
        // Creating Billing Account
        BillingResponse br = grpcClient.createBillingAccount(patient.getName(),patient.getId().toString(),patient.getEmail());

        kafkaProducer.sendEvent(recivedPatient);
        return PatientToDTO.toDTO(patient);
    }

    // Handles business logic to update patient

    public PatientDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id).
                orElseThrow(()-> new PatientNotFoundException("Patient Not Found with Id :" + id));

        // Need to check if the email requested to update exists or not
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){

            throw new EmailExistsException("Email "+ patientRequestDTO.getEmail() + " is already registered");
        }

        patient.setEmail(patientRequestDTO.getEmail()== null || patientRequestDTO.getEmail().isBlank() ? patient.getEmail() : patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress() == null|| patientRequestDTO.getAddress().isBlank() ? patient.getAddress(): patientRequestDTO.getAddress());
        patient.setName(patientRequestDTO.getName() == null || patientRequestDTO.getName().isBlank()? patient.getName() : patientRequestDTO.getName());
        patient.setDob(patientRequestDTO.getDob()== null || patientRequestDTO.getDob().isBlank()? patient.getDob() : LocalDate.parse(patientRequestDTO.getDob()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientToDTO.toDTO(updatedPatient);

    }
}
