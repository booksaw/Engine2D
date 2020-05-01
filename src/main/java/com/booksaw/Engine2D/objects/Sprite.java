package main.java.com.booksaw.Engine2D.objects;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.rendering.animation.AnimationManager;

public class Sprite extends ImageObject {

	private int player;
	private double accelerationX = 0.5, decelerationX = 0.75, accelerationJump = 3, accelerationGravity = -0.5,
			maxJumpTicks = 20;
	double maxSpeedX = 2.5, maxSpeedY = -1, maxSpeed = -1;

	public Sprite(AnimationManager manager, GameManager gameManager, int player) {
		super(manager, gameManager);
		this.player = player;
	}

	@Override
	public void update(int time) {
		super.update(time);
		move(time);
	}

	/**
	 * Used to track the status of jumping -1 means the player can jump. -2 means
	 * the player cannot jump. -3 means the player is in the process of jumping
	 * (used to determine the jump height)
	 */
	private int ticksJumping = -1;

	/**
	 * A simple movement script so players can control an object
	 * 
	 * @param time the number of ticks since last update
	 */
	public void move(int time) {
		// running each tick individually
		for (int i = 0; i < time; i++) {

			moveHorizontal();
			moveVertical();
			if (ticksJumping != -1) {
				applyGravity();
			}
		}

	}

	/**
	 * Used to carry out the movement of a single horizontal tick
	 */
	public void moveHorizontal() {
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

	/**
	 * Used to do jump calculations
	 */
	public void moveVertical() {

		if (KeyboardManager.keyboardManager.isActive("player." + player + ".up")) {
			if (ticksJumping == -1) {
				// player can jump
				jump();
			} else if (ticksJumping >= 0) {
				jumpCheck();
			}
		} else if (ticksJumping >= 0) {

			// checks for if up removed on jump tick
			ticksJumping = -2;

		}

	}

	/**
	 * Used when the sprite initially jumps
	 */
	public void jump() {
		velocity.applyVector(new Vector(0, accelerationJump), -1, maxSpeedY, maxSpeed, false);
		ticksJumping = 0;
	}

	/**
	 * Used to apply the velocities if the player is still pressing the jump key
	 */
	public void jumpCheck() {
		Logger.Log("checking");
		if (ticksJumping > maxJumpTicks) {
			// player can no longer hold jump key, but cannot jump
			ticksJumping = -2;
			return;
		}

		// stopping gravity effect
		velocity.applyVector(new Vector(0, -accelerationGravity));
		ticksJumping++;

	}

	/**
	 * Used to apply the acceleration due to gravity
	 */
	public void applyGravity() {
		velocity.applyVector(new Vector(0, accelerationGravity), -1, maxSpeedY, maxSpeed, false);
	}

}
