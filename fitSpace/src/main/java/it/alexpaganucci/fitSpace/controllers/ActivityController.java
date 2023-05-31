package it.alexpaganucci.fitSpace.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.alexpaganucci.fitSpace.entities.Activity;
import it.alexpaganucci.fitSpace.entities.User;
import it.alexpaganucci.fitSpace.exceptions.ActivityNotFoundException;
import it.alexpaganucci.fitSpace.exceptions.UserNotFoundException;
import it.alexpaganucci.fitSpace.repositories.UserRepository;
import it.alexpaganucci.fitSpace.services.ActivityService;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

	@Autowired
	private ActivityService actSrv;
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/save/{id}")	
	public ResponseEntity<?> saveActivity(@Valid @PathVariable Long id, @RequestBody Activity activity) {
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
		try {
			Activity userActivity = actSrv.addActivity(user, activity);
			return ResponseEntity.ok().build();
		} catch (ActivityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Nessuna attività trovata con id " + id);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Errore durante l'elaborazione della richiesta");
	    }
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateActivity(@Valid @PathVariable Long id, @RequestBody Activity activity){
		try {
			Activity userActivity = actSrv.updateActivity(id, activity);
			return ResponseEntity.ok().build();
		} catch (ActivityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Nessuna attività trovata con id " + id);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Errore durante l'elaborazione della richiesta");
	    }

	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
	    try {
	        actSrv.deleteActivity(id);
	        return ResponseEntity.ok().build();
	    } catch (ActivityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Nessuna attività trovata con id " + id);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Errore durante l'elaborazione della richiesta");
	    }
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<?> getAllActivity(@PathVariable Long userId) {
	    try {
	        List<Activity> activities = actSrv.findAllByUser(userId)
	                .orElseThrow(() -> new ActivityNotFoundException("Attività non trovate"));

	        return ResponseEntity.ok(activities);
	    } catch (ActivityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Nessuna attività trovata per l'utente specificato");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Errore durante l'elaborazione della richiesta");
	    }
	}
	
	
	
}
