package it.alexpaganucci.fitSpace.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.alexpaganucci.fitSpace.entities.Goal;
import it.alexpaganucci.fitSpace.entities.User;
import it.alexpaganucci.fitSpace.entities.enums.TypeOfActivity;
import it.alexpaganucci.fitSpace.exceptions.MessageResponse;
import it.alexpaganucci.fitSpace.exceptions.UserNotFoundException;
import it.alexpaganucci.fitSpace.payloads.UserUpdateRequest;
import it.alexpaganucci.fitSpace.repositories.UserRepository;
import it.alexpaganucci.fitSpace.services.ActivityRecommendationService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
    private ActivityRecommendationService activityRecommendationService;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
    
	@PutMapping("/update_goal/{userId}")
	public ResponseEntity<?> updateGoal(@PathVariable Long userId, @RequestBody Goal goal) {
	    User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	    
	    // Verifica se il TypeGoal è diverso da quello attuale dell'utente
	    if (!user.getGoal().getTypeGoal().equals(goal.getTypeGoal())) {
	        // Aggiorna il TypeGoal dell'utente
	        user.getGoal().setTypeGoal(goal.getTypeGoal());
	        
	        // Richiama il servizio di raccomandazione attività per ottenere le nuove recommendedActivities
	        List<TypeOfActivity> recommendedActivities = activityRecommendationService.recommendActivities(user);
	        user.getGoal().setRecommendedActivities(recommendedActivities);
	        
	        // Salva nel database l'utente modificato
	        userRepo.save(user);
	        
	        // Restituisci la risposta con l'utente aggiornato
	        return ResponseEntity.ok().build();
	    } else {
	        // Il TypeGoal fornito è uguale a quello attuale dell'utente
	        return ResponseEntity.badRequest().body(new MessageResponse("Il TypeGoal fornito è uguale a quello attuale dell'utente."));
	    }
	}
    
    @PutMapping("/update_user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));

        // Aggiorna i campi desiderati dell'utente
        user.setName(userUpdateRequest.getName());
        user.setSurname(userUpdateRequest.getSurname());
        user.setBirthdate(userUpdateRequest.getBirthdate());
        user.setWeight(userUpdateRequest.getWeight());
        user.setHeight(userUpdateRequest.getHeight());
        
        // Aggiorna il campo goal solo se è presente nel payload
        if (userUpdateRequest.getGoal() != null) {
            user.setGoal(userUpdateRequest.getGoal());
        }

        // Salva nel database il nuovo User
        User updatedUser = userRepo.save(user);

        // Restituisci la risposta con l'utente aggiornato
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));

        userRepo.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
