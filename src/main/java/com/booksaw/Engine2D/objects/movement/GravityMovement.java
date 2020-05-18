package main.java.com.booksaw.Engine2D.objects.movement;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.objects.Sprite;

/**
 * This applies gravity every time it is updated
 * 
 * @author booksaw
 *
 */
public class GravityMovement extends Movement {

	/**
	 * The reference for this movement type
	 */
	protected static String reference = "gravity";

	/**
	 * Used to get the unique reference for this movement type in a static way
	 * 
	 * @return the unique reference for this movement
	 */
	public static String getStaticReference() {
		return reference;
	}

	/**
	 * @param sprite      The sprite to apply the gravity to
	 * @param information Information about the movement (for this movement use ""
	 *                    as no information is loaded)
	 */
	public GravityMovement(Sprite sprite, String information) {
		super(sprite, information);
	}

	/**
	 * Used to apply gravity to the sprite
	 */
	@Override
	public void update(Vector velocity) {
		if (!sprite.getManager().level.isColliding(sprite.getShape(new Vector(0, -1)), sprite)) {
			velocity.applyVector(new Vector(0, sprite.getManager().accelerationGravity), -1, sprite.getMaxSpeedY(),
					sprite.getMaxSpeed(), false);
		}
	}

	@Override
	public String getOutput() {
		return "0";
	}

	@Override
	public String getReference() {
		return reference;
	}

}
