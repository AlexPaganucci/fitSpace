package it.alexpaganucci.fitSpace.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.alexpaganucci.fitSpace.entities.Goal;
import it.alexpaganucci.fitSpace.entities.User;
import it.alexpaganucci.fitSpace.entities.enums.TypeOfActivity;
import it.alexpaganucci.fitSpace.entities.enums.TypeOfGoal;

@Service
public class ActivityRecommendationService {

    public List<TypeOfActivity> recommendActivities(User user) {
        // Analizza il goal dell'utente e filtra le attività pertinenti
        // in base alle regole di raccomandazione definite
        
        // Restituisci un elenco di attività raccomandate
    	
        List<TypeOfActivity> recommendedActivities = new ArrayList<>();
        Goal goal = user.getGoal();
        
        // Analizza il goal dell'utente e filtra le attività pertinenti
        if (goal != null) {
            TypeOfGoal typeGoal = goal.getTypeGoal();
            switch (typeGoal) {
                case LOSE_WEIGHT:
                    recommendedActivities.add(TypeOfActivity.RUN);
                    recommendedActivities.add(TypeOfActivity.GYM);
                    recommendedActivities.add(TypeOfActivity.SWIM);
                    recommendedActivities.add(TypeOfActivity.CYCLING);
                    recommendedActivities.add(TypeOfActivity.WALK);
                    break;
                case GAIN_WEIGHT:
                    recommendedActivities.add(TypeOfActivity.GYM);
                    break;
                case RESISTENCE_IMPROVEMENT:
                    recommendedActivities.add(TypeOfActivity.RUN);
                    recommendedActivities.add(TypeOfActivity.WALK);
                    recommendedActivities.add(TypeOfActivity.CYCLING);
                    recommendedActivities.add(TypeOfActivity.SWIM);
                    break;
                case STRENGHT_IMPROVEMENT:
                	recommendedActivities.add(TypeOfActivity.GYM);
                	break;
                case TIME_REDUCTION:
                    recommendedActivities.add(TypeOfActivity.RUN);
                    recommendedActivities.add(TypeOfActivity.WALK);
                    recommendedActivities.add(TypeOfActivity.CYCLING);
                    recommendedActivities.add(TypeOfActivity.SWIM);
                	break;
                default:
                	recommendedActivities.add(TypeOfActivity.RUN);
                	recommendedActivities.add(TypeOfActivity.GYM);
                	recommendedActivities.add(TypeOfActivity.SWIM);
                	recommendedActivities.add(TypeOfActivity.CYCLING);
                	recommendedActivities.add(TypeOfActivity.WALK);
            }
        }
        
        return recommendedActivities;
    }
}
