package it.alexpaganucci.fitSpace.payloads;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.alexpaganucci.fitSpace.entities.enums.TypeOfGoal;
import it.alexpaganucci.fitSpace.validators.StrongPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

	@NotBlank
	@Email
	private String email;
	private String name;
	private String surname;
	private Set<String> roles;
	@StrongPassword
	@NotBlank
	@Size(min = 8, max = 40)
	private String password;
	@NotBlank
	@Size(min = 8, max = 40)
	private String confirmPassword;
	private double weight;
	private double height;
	private TypeOfGoal typeGoal;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthdate;
}
