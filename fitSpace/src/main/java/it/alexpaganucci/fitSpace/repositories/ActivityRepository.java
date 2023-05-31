package it.alexpaganucci.fitSpace.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alexpaganucci.fitSpace.entities.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM activities  WHERE user_id = :u")
	Optional<List<Activity>> findActivitiesByUser(@Param ("u") Long userId);
}
