package main.java.com.booksaw.Engine2D.objects.movement;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.objects.Sprite;

public class JumpMovement extends Movement {

	private Sprite sprite;

	/**
	 * Used to track the status of jumping -1 means the player can jump. -2 means
	 * the player cannot jump. -3 means the player is in the process of jumping
	 * (used to determine the jump height)
	 */
	private transient int ticksJumping = -1;

	private int maxJumpTicks = 100;
	private double accelerationJump;

	public JumpMovement(Sprite sprite, String information) {
		super(sprite, information);
		this.sprite = sprite;
		String[] split = information.split(",");

		maxJumpTicks = Integer.parseInt(split[0]);
		accelerationJump = Integer.parseInt(split[1]);

	}

	@Override
	public void update(Vector velocity) {
		if (KeyboardManager.keyboardManager.isActive("player." + sprite.player + ".up")) {
			if (ticksJumping == -1) {
				// player can jump
				jump(velocity);
			} else if (ticksJumping >= 0) {
				jumpCheck(velocity);
			}
		} else if (ticksJumping >= 0) {

			// checks for if up removed on jump tick
			ticksJumping = -2;

		}
	}

	@Override
	public String getOutput() {
		return maxJumpTicks + "," + accelerationJump;
	}

	/**
	 * Used to do jump calculations
	 */
	public void moveVertical() {

	}

	/**
	 * Used when the sprite initially jumps
	 */
	public void jump(Vector velocity) {
		velocity.applyVector(new Vector(0, accelerationJump), -1, sprite.maxSpeedY, sprite.maxSpeed, false);
		ticksJumping = 0;
	}

	/**
	 * Used to apply the velocities if the player is still pressing the jump key
	 */
	public void jumpCheck(Vector velocity) {
		if (ticksJumping > maxJumpTicks) {
			// player can no longer hold jump key, but cannot jump
			ticksJumping = -2;
			return;
		}

		// stopping gravity effect
		velocity.applyVector(new Vector(0, -sprite.getManager().accelerationGravity));
		ticksJumping++;

	}

}
