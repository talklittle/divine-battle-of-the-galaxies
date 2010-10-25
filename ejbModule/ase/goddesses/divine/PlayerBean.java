package ase.goddesses.divine;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class PlayerBean
 */
@Stateful
@LocalBean
public class PlayerBean {

	private boolean isAuthenticated = false;
	
    /**
     * Default constructor. 
     */
    public PlayerBean() {
        isAuthenticated = false;
    }
    
    public boolean authenticate(String username, String password) {
    	// TODO
    	
    	return isAuthenticated;
    }
    
    public void move(int direction) {
    	switch (direction) {
    	case Directions.DIRECTION_UP:
    		
    		break;
    	case Directions.DIRECTION_LEFT:
    		
    		break;
    	case Directions.DIRECTION_DOWN:
    		
    		break;
    	case Directions.DIRECTION_RIGHT:
    		
    		break;
    	}
    }
    
    public History getHistory() {
    	// TODO
    }
    
    public int getNumStars() {
    	// TODO
    }
    
    

}
