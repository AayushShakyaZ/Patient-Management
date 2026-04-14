package ms.patientservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ms.patientservice.entity.Patient;
import ms.patientservice.model.PatientDto;
import ms.patientservice.model.PatientRequestDto;
import ms.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @RequestMapping("/list")
    public ResponseEntity<List<PatientDto>> allPatients(){

        List<PatientDto> patientDtos = patientService.getPatients();

        return ResponseEntity.ok().body(patientDtos);
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto){

        PatientDto patientDto = patientService.createPatient(patientRequestDto);

        return ResponseEntity.ok().body(patientDto);
    }


}
