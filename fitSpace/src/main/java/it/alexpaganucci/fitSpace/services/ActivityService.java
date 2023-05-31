package it.alexpaganucci.fitSpace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.alexpaganucci.fitSpace.entities.Activity;
import it.alexpaganucci.fitSpace.entities.User;
import it.alexpaganucci.fitSpace.exceptions.ActivityNotFoundException;
import it.alexpaganucci.fitSpace.repositories.ActivityRepository;
import it.alexpaganucci.fitSpace.utils.Calculator;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository actRepo;
	@Autowired
	private Calculator calculator;
	
    public Activity addActivity(User user, Activity activity) {
    	Activity userActivity = Activity.builder().user(user)
    			.typeActivity(activity.getTypeActivity())
    			.duration(activity.getDuration())
    			.distance(activity.getDistance())
    			.build();
    	double calories = calculator.calorieCalculator(activity.getTypeActivity(), user.getWeight(), activity.getDuration());
    	userActivity.setCalories(calories);
        return actRepo.save(userActivity);
    }

    public Activity updateActivity(Long id, Activity activity) {
        Activity userActivity = actRepo.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with id: " + id));

        userActivity.setTypeActivity(activity.getTypeActivity());
        userActivity.setDuration(activity.getDuration());
        userActivity.setDistance(activity.getDistance());
        //da controllare domani TODO
    	double calories = calculator.calorieCalculator(activity.getTypeActivity(), userActivity.getUser().getWeight(), activity.getDuration());
    	userActivity.setCalories(calories);
        return actRepo.save(userActivity);
    }

    public void deleteActivity(Long id) {
        Activity activity = actRepo.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with id: " + id));

        actRepo.delete(activity);
    }
	
    public Optional<List<Activity>> findAllByUser(Long userId){
    	Optional<List<Activity>> activities = actRepo.findActivitiesByUser(userId);
    	return activities;
    }
	
}
