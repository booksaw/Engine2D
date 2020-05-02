package main.java.com.booksaw.Engine2D.objects.movement;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.objects.Sprite;

/**
 * Used to create a new movement which can be used to update the velocity of an
 * object
 * 
 * @author booksaw
 *
 */
public abstract class Movement {

	/**
	 * Used to load the information required for this class
	 * 
	 * @param information
	 */
	public Movement(Sprite sprite, String information) {
	}

	/**
	 * Used to update the vector based on the move set provided by this class
	 * 
	 * @param velocity the velocity to update
	 */
	public abstract void update(Vector velocity);

	/**
	 * The output so this movement can be saved to file and re-loaded with the same
	 * information
	 * 
	 * @return the output
	 */
	public abstract String getOutput();

}
