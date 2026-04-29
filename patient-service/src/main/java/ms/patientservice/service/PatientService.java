package ms.patientservice.service;

import lombok.RequiredArgsConstructor;
import ms.patientservice.entity.Patient;
import ms.patientservice.exception.EmailAlreadyExistException;
import ms.patientservice.exception.PatientNotFoundException;
import ms.patientservice.grpc.BillingServiceGrpcClient;
import ms.patientservice.kafka.KafkaProducer;
import ms.patientservice.mapper.PatientMapper;
import ms.patientservice.dto.PatientDto;
import ms.patientservice.dto.PatientRequestDto;
import ms.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public List<PatientDto> getPatients(){

        List<Patient> patients = patientRepository.findAll();

        List<PatientDto> patientDtos = patients.stream().map(patientMapper::toDto).toList();

        return patientDtos;
    }

    public PatientDto createPatient(PatientRequestDto patientRequestDto){

        if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailAlreadyExistException("Patient with same email already exists"+ patientRequestDto.getEmail());
        }
        Patient newPatient = patientRepository.save(patientMapper.toEntity(patientRequestDto));

        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return patientMapper.toDto(newPatient);
    }

    public PatientDto updatePatient(UUID id, PatientRequestDto patientRequestDTO) {

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistException("A patient with this email " + "already exists"
                            + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toDto(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }


}
