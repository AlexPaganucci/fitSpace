package it.alexpaganucci.fitSpace;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.alexpaganucci.fitSpace.entities.Role;
import it.alexpaganucci.fitSpace.entities.User;
import it.alexpaganucci.fitSpace.entities.enums.RoleType;
import it.alexpaganucci.fitSpace.repositories.RoleRepository;
import it.alexpaganucci.fitSpace.repositories.UserRepository;

@SpringBootApplication
public class FitSpaceApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(FitSpaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		User u = new User("alex.paganucci@gmail.com", "Alex", "Paganucci", passwordEncoder.encode("Alexpaga1994"), LocalDate.of(1994, 7, 9));
//		Role rU = new Role(RoleType.USER);
//		Role rA = new Role(RoleType.ADMIN);
//		u.setRoles(new HashSet<>() {{
//			add(rU);
//			add(rA);
//		}});
//		roleRepository.save(rU);
//		roleRepository.save(rA);
//		userRepository.save(u);
		
	}

}
