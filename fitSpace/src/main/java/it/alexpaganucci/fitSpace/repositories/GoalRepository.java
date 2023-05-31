package it.alexpaganucci.fitSpace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.alexpaganucci.fitSpace.entities.Goal;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long>{

}
