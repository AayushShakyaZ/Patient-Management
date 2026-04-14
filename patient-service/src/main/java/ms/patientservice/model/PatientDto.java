package ms.patientservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDto {

    private String id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;

}
