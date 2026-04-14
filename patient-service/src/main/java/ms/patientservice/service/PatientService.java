package ms.patientservice.service;

import lombok.RequiredArgsConstructor;
import ms.patientservice.entity.Patient;
import ms.patientservice.mapper.PatientMapper;
import ms.patientservice.model.PatientDto;
import ms.patientservice.model.PatientRequestDto;
import ms.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getPatients(){

        List<Patient> patients = patientRepository.findAll();

        List<PatientDto> patientDtos = patients.stream().map(patientMapper::toDto).toList();

        return patientDtos;
    }

    public PatientDto createPatient(PatientRequestDto patientRequestDto){

        Patient newPatient = patientRepository.save(patientMapper.toEntity(patientRequestDto));

        return patientMapper.toDto(newPatient);
    }
}
