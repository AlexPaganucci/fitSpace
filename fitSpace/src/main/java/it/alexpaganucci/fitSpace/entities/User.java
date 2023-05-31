package it.alexpaganucci.fitSpace.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="email", unique = true)
	@NotBlank
	@Email
	private String email;
	@Column(name="name")
	private String name;
	@Column(name="surname")
	private String surname;
	@Column(name="password")
	@NotBlank
	private String password;
	@JsonFormat
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<Role> roles = new HashSet<>();
	@Column(name="birthdate")
	private LocalDate birthdate;
	@Column(name="weight")
	@Min(value = 0, message = "Il peso non può essere negativo")
	@Max(value = 500, message = "Il peso massimo consentito è 500")
	private double weight;
	@Column(name="height")
	@Min(value = 0, message = "L'altezza non può essere negativa")
	@Max(value = 300, message = "L'altezza massima consentita è 300")
	private double height;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonFormat
	private Goal goal;
	@OneToMany(mappedBy = "user")
	private List<Activity> activities;
	
	
	public User(String email, String name, String surname, String password, LocalDate birthdate) {
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.birthdate = birthdate;
	}
	
    public void setGoal(Goal goal) {
        this.goal = goal;
        goal.setUser(this); // Imposta l'utente nella classe Goal
    }
}
