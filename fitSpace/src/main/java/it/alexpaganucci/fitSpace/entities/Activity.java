package it.alexpaganucci.fitSpace.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.alexpaganucci.fitSpace.entities.enums.TypeOfActivity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "activities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="type_of_activity")
	@Enumerated(EnumType.STRING)
	private TypeOfActivity typeActivity;
	@Column(name="duration")
	private double duration;
	@Column(name="distance")
	private double distance;
	@Column(name="calories")
	private double calories;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Activity(User user, TypeOfActivity typeActivity, double duration, double distance) {
		this.user = user;
		this.typeActivity = typeActivity;
		this.duration = duration;
		this.distance = distance;
		this.calories = 0;
	}
	
}
