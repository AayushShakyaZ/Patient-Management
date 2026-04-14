package ms.patientservice.mapper;

import ms.patientservice.entity.Patient;
import ms.patientservice.model.PatientDto;
import ms.patientservice.model.PatientRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toDto(Patient patient);

    Patient toEntity(PatientRequestDto patientDto);
}
