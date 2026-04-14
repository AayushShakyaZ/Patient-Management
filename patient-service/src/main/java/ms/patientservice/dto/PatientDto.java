package ms.patientservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDto {

    private String id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;

}
