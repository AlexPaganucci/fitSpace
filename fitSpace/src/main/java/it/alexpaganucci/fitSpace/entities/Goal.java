package it.alexpaganucci.fitSpace.entities;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.alexpaganucci.fitSpace.entities.enums.TypeOfActivity;
import it.alexpaganucci.fitSpace.entities.enums.TypeOfGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "goals")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Goal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name="userID")
	private User user;
	@Column(name="type_of_goal")
	@Enumerated(EnumType.STRING)
	private TypeOfGoal typeGoal;
    @ElementCollection(targetClass = TypeOfActivity.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "goal_activities", joinColumns = @JoinColumn(name = "goal_id"))
	@Column(name="recommended_activities")
	List<TypeOfActivity> recommendedActivities;

}
