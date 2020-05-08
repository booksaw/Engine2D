package main.java.com.booksaw.Engine2D.objects.movement;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.objects.Sprite;

/**
 * Used for user controlled horizontal movement (accelerationX,decelerationX)
 * 
 * @author booksaw
 *
 */
public class HorizontalMovement extends Movement {

	protected static String reference = "horizontal";

	public static String getStaticReference() {
		return reference;
	}

	private double accelerationX, decelerationX;
	Sprite sprite;

	public HorizontalMovement(Sprite sprite, String information) {
		super(sprite, information);
		String[] split = information.split(",");
		accelerationX = Double.parseDouble(split[0]);
		decelerationX = Double.parseDouble(split[1]);
		this.sprite = sprite;
	}

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
