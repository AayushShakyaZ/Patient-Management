package ms.patientservice.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import ms.patientservice.dto.PatientDto;
import ms.patientservice.dto.PatientRequestDto;
import ms.patientservice.dto.validators.CreatePatientValidationGroup;
import ms.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/list")
    public ResponseEntity<List<PatientDto>> allPatients(){

        List<PatientDto> patientDtos = patientService.getPatients();

        return ResponseEntity.ok().body(patientDtos);
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDto> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class})
                                                        @RequestBody PatientRequestDto patientRequestDto){

        PatientDto patientDto = patientService.createPatient(patientRequestDto);

        return ResponseEntity.ok().body(patientDto);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody
    PatientRequestDto patientRequestDTO) {

        PatientDto patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
