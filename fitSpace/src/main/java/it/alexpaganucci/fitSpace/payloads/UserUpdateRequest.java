package it.alexpaganucci.fitSpace.payloads;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.alexpaganucci.fitSpace.entities.Goal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    private String name;
    private String surname;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;
    private double weight;
    private double height;
    private Goal goal;
}
