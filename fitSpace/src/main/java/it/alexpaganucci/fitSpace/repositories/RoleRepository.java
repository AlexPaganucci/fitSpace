package it.alexpaganucci.fitSpace.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.alexpaganucci.fitSpace.entities.Role;
import it.alexpaganucci.fitSpace.entities.enums.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleType r);
}
