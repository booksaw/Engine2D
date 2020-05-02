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

	private double accelerationX, decelerationX;
	Sprite sprite;

	public HorizontalMovement(Sprite sprite, String information) {
		super(sprite, information);
		String[] split = information.split(",");
		accelerationX = Integer.parseInt(split[0]);
		decelerationX = Integer.parseInt(split[1]);
		this.sprite = sprite;
	}

	@Override
	public void update(Vector velocity) {
		if (KeyboardManager.keyboardManager.isActive("player." + sprite.player + ".left")) {
			velocity.applyVector(new Vector(-accelerationX, 0), sprite.maxSpeedX, -1, sprite.maxSpeed, false);
		} else if (velocity.x < 0) {
			velocity.applyVector(new Vector(decelerationX, 0), -1, -1, sprite.maxSpeed, true);
		}
		if (KeyboardManager.keyboardManager.isActive("player." + sprite.player + ".right")) {
			velocity.applyVector(new Vector(accelerationX, 0), sprite.maxSpeedX, -1, sprite.maxSpeed, false);
		} else if (velocity.x > 0) {
			velocity.applyVector(new Vector(-decelerationX, 0), -1, -1, sprite.maxSpeed, true);
		}
	}

	@Override
	public String getOutput() {
		return accelerationX + "," + decelerationX;
	}

}
