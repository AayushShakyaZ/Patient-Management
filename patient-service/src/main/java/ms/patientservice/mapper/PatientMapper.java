package ms.patientservice.mapper;

import ms.patientservice.entity.Patient;
import ms.patientservice.dto.PatientDto;
import ms.patientservice.dto.PatientRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toDto(Patient patient);

    Patient toEntity(PatientRequestDto patientDto);
}
