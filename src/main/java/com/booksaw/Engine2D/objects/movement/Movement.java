package main.java.com.booksaw.Engine2D.objects.movement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
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
	 * Used so all game objects can be tracked
	 */
	private static HashMap<String, Class<?>> movementTypes;

	/**
	 * Adding all default game objects
	 */
	static {
		movementTypes = new HashMap<>();
		movementTypes.put(GravityMovement.getReference(), GravityMovement.class);
		movementTypes.put(HorizontalMovement.getReference(), HorizontalMovement.class);
		movementTypes.put(JumpMovement.getReference(), JumpMovement.class);

	}

	/**
	 * Used to create an object from the provided node
	 * 
	 * @param node the details about that object
	 */
	public static Movement createObjectFromData(Sprite sprite, String info) {
		String[] split = info.split(";");

		Class<?> theClass = movementTypes.get(split[0]);

		try {
			Constructor<?> construct = theClass.getDeclaredConstructor(new Class[] { Sprite.class, String.class });
			Movement movement = (Movement) construct.newInstance(sprite, split[1]);
			return movement;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Logger.Log(LogType.ERROR, "Could not create an instance of the class: " + theClass);
			Logger.Log(e.toString());
			return null;
		}
	}

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
