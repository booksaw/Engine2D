package main.java.com.booksaw.Engine2D.objects.movement;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.objects.Sprite;

/**
 * Used for user controlled horizontal movement (accelerationX, decelerationX)
 * 
 * @author booksaw
 *
 */
public class HorizontalMovement extends Movement {

	/**
	 * The reference for this movement type
	 */
	protected static String reference = "horizontal";

	/**
	 * Used to get the unique reference for this movement type in a static way
	 * 
	 * @return the unique reference for this movement
	 */
	public static String getStaticReference() {
		return reference;
	}

	/**
	 * Constants to affect the speed of acceleraation
	 * <p>
	 * Acceleration is done in pixels per tick, check CONFIG to see the length of a
	 * tick
	 * </p>
	 */
	private double accelerationX, decelerationX;

	/**
	 * Used to load the movement information from file
	 * 
	 * @param sprite      the sprite to which this movement applies to
	 * @param information the information about this movement ['accelerationX,decelerationX']
	 */
	public HorizontalMovement(Sprite sprite, String information) {
		super(sprite, information);
		String[] split = information.split(",");
		accelerationX = Double.parseDouble(split[0]);
		decelerationX = Double.parseDouble(split[1]);
	}

	/**
	 * Used to apply the horizontal motion to the user
	 */
	@Override
	public void update(Vector velocity) {
		if (KeyboardManager.keyboardManager.isActive("player." + sprite.getPlayer() + ".left")) {
			velocity.applyVector(new Vector(-accelerationX, 0), sprite.getMaxSpeedX(), -1, sprite.getMaxSpeed(), false);
		} else if (velocity.x < 0) {
			velocity.applyVector(new Vector(decelerationX, 0), -1, -1, sprite.getMaxSpeed(), true);
		}
		if (KeyboardManager.keyboardManager.isActive("player." + sprite.getPlayer() + ".right")) {
			velocity.applyVector(new Vector(accelerationX, 0), sprite.getMaxSpeedX(), -1, sprite.getMaxSpeed(), false);
		} else if (velocity.x > 0) {
			velocity.applyVector(new Vector(-decelerationX, 0), -1, -1, sprite.getMaxSpeed(), true);
		}
	}

	@Override
	public String getOutput() {
		return accelerationX + "," + decelerationX;
	}

	@Override
	public String getReference() {
		return reference;
	}

}
