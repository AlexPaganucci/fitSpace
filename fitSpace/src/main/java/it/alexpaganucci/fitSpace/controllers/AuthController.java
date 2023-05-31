package it.alexpaganucci.fitSpace.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.alexpaganucci.fitSpace.auth.AccessDetails;
import it.alexpaganucci.fitSpace.entities.Goal;
import it.alexpaganucci.fitSpace.entities.Role;
import it.alexpaganucci.fitSpace.entities.User;
import it.alexpaganucci.fitSpace.entities.enums.RoleType;
import it.alexpaganucci.fitSpace.entities.enums.TypeOfActivity;
import it.alexpaganucci.fitSpace.exceptions.MessageResponse;
import it.alexpaganucci.fitSpace.exceptions.RoleNotFoundException;
import it.alexpaganucci.fitSpace.payloads.JwtResponse;
import it.alexpaganucci.fitSpace.payloads.LoginRequest;
import it.alexpaganucci.fitSpace.payloads.SignupRequest;
import it.alexpaganucci.fitSpace.repositories.GoalRepository;
import it.alexpaganucci.fitSpace.repositories.RoleRepository;
import it.alexpaganucci.fitSpace.repositories.UserRepository;
import it.alexpaganucci.fitSpace.services.ActivityRecommendationService;
import it.alexpaganucci.fitSpace.utils.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	GoalRepository goalRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	ActivityRecommendationService activityRecService;

	@PostMapping("/login")	
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication a = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		return returnToken(a);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, BindingResult result) {
	    if (result.hasErrors()) {
	        List<String> errors = result.getAllErrors().stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .collect(Collectors.toList());
	        return ResponseEntity.badRequest().body(new MessageResponse("Validation errors: " + errors));
	    }
	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use."));
	    }
	    if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Passwords do not match."));
	    }
	    // Create a user Goal
	    Goal goal = Goal.builder().typeGoal(signUpRequest.getTypeGoal())
	    		.build();
	    		
	    // Create new user's account
	    User user = User.builder().email(signUpRequest.getEmail())
	            .password(passwordEncoder.encode(signUpRequest.getPassword()))
	            .name(signUpRequest.getName())
	            .surname(signUpRequest.getSurname())
	            .birthdate(signUpRequest.getBirthdate())
	            .weight(signUpRequest.getWeight())
	            .height(signUpRequest.getHeight())
	            .goal(goal)
	            .build();
	    
	    List<TypeOfActivity> recommendedActivities = activityRecService.recommendActivities(user);
	    goal.setRecommendedActivities(recommendedActivities);
	    user.setGoal(goal);


	    Set<String> roles = signUpRequest.getRoles();
	    Set<Role> userRoles = new HashSet<>();
	    if (roles == null) {
	        Role userRole = roleRepository.findByName(RoleType.USER).orElseThrow(() -> new RoleNotFoundException("Error: Role not found."));
	        userRoles.add(userRole);
	    } else {
	        for (String role : roles) {
	            RoleType roleType = EnumUtils.getEnumIgnoreCase(RoleType.class, role);
	            if (roleType != null) {
	                Optional<Role> optionalRole = roleRepository.findByName(roleType);
	                if (optionalRole.isPresent()) {
	                    userRoles.add(optionalRole.get());
	                } else {
	                    return ResponseEntity.badRequest().body(new MessageResponse("Invalid user role specified."));
	                }
	            } else {
	                return ResponseEntity.badRequest().body(new MessageResponse("Invalid user role specified."));
	            }
	        }
	    }
	    user.setRoles(userRoles);

	    try {
	        userRepository.save(user);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Unable to create user account. Please try again later."));
	    }

	    return returnToken(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), signUpRequest.getPassword())));
	}
	
	public ResponseEntity<?> returnToken(Authentication a) {
		SecurityContextHolder.getContext().setAuthentication(a);
		String jwt = jwtUtils.generateJwtToken(a);		
		AccessDetails uD = (AccessDetails) a.getPrincipal();		
		List<String> roles = uD.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, uD.getId(), uD.getEmail(), roles));
	}
}
