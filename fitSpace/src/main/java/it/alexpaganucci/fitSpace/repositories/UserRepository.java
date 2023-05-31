package it.alexpaganucci.fitSpace.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alexpaganucci.fitSpace.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE email = :e")
	Optional<User> findUserByEmail(@Param("e") String e);
	
	Boolean existsByEmail(String e);
}
