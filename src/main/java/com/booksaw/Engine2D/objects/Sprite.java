package main.java.com.booksaw.Engine2D.objects;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.rendering.animation.AnimationManager;

public class Sprite extends ImageObject {

	private int player;
	private double accelerationX = 0.5, decelerationX = 0.75;
	double maxSpeedX = 2.5, maxSpeedY = -1, maxSpeed = -1;

	public Sprite(AnimationManager manager, GameManager gameManager, int player) {
		super(manager, gameManager);
	}

	@Override
	public void update(int time) {
		super.update(time);
		move(time);
	}

	/**
	 * A simple movement script so players can control an object
	 * 
	 * @param time the number of ticks since last update
	 */
	public void move(int time) {
		// running each tick individually
		for (int i = 0; i < time; i++) {

			if (KeyboardManager.keyboardManager.isActive("player." + player + ".left")) {
				velocity.applyVector(new Vector(-accelerationX, 0), maxSpeedX, -1, maxSpeed, false);
			} else if (velocity.x < 0) {
				velocity.applyVector(new Vector(decelerationX, 0), -1, -1, maxSpeed, true);
			}
			if (KeyboardManager.keyboardManager.isActive("player." + player + ".right")) {
				velocity.applyVector(new Vector(accelerationX, 0), maxSpeedX, -1, maxSpeed, false);
			} else if (velocity.x > 0) {
				velocity.applyVector(new Vector(-decelerationX, 0), -1, -1, maxSpeed, true);
			}

		}

	}

}
