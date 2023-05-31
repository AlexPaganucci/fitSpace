package it.alexpaganucci.fitSpace.utils;

import org.springframework.stereotype.Component;

import it.alexpaganucci.fitSpace.entities.enums.TypeOfActivity;

@Component
public class Calculator {

	public double calorieCalculator(TypeOfActivity typeActivity, double weight, double duration) {
	    double calories = 0.0;
	    
	    switch (typeActivity) {
	        case RUN:
	            // Utilizza un valore MET per la corsa (ad esempio, 7.0)
	            double metRun = 7.0;
	            calories = metRun * weight * duration;
	            break;
	        
	        case WALK:
	            // Utilizza un valore MET per la camminata (ad esempio, 3.5)
	            double metWalk = 3.5;
	            calories = metWalk * weight * duration;
	            break;
	        
	        case CYCLING:
	            // Utilizza un valore MET per il ciclismo (ad esempio, 6.0)
	            double metCycling = 6.0;
	            calories = metCycling * weight * duration;
	            break;
	        
	        case SWIM:
	            // Utilizza un valore MET per il nuoto (ad esempio, 8.0)
	            double metSwim = 8.0;
	            calories = metSwim * weight * duration;
	            break;
	        
	        case GYM:
	            // Utilizza un valore MET per l'allenamento in palestra (ad esempio, 5.0)
	            double metGym = 5.0;
	            calories = metGym * weight * duration;
	            break;
	            
	        default:
	            System.out.println("Tipo di attività non valido.");
	            break;
	    }
	    
	    return calories;
	}
}
