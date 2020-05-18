package main.java.com.booksaw.Engine2D.objects.movement;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.objects.Sprite;

/**
 * This class allows sprites which include this movement to have player
 * controlled jump
 * 
 * @author booksaw
 *
 */
public class JumpMovement extends Movement {

	/**
	 * The reference for this movement type
	 */
	protected static String reference = "jump";

	/**
	 * Used to get the unique reference for this movement type in a static way
	 * 
	 * @return the unique reference for this movement
	 */
	public static String getStaticReference() {
		return reference;
	}

	/**
	 * Used to track the status of jumping -1 means the player can jump. -2 means
	 * the player cannot jump. -3 means the player is in the process of jumping
	 * (used to determine the jump height)
	 */
	private transient int ticksJumping = -1;

	/**
	 * The maximum number of ticks that the player can have gravity disabled (to
	 * make jumping feel more responsive, while the player holds down the jump key,
	 * gravity is disabled)
	 */
	private int maxJumpTicks = 100;

	/**
	 * The initial acceleration of the jump, this is only applied a single time per
	 * jump
	 */
	private double accelerationJump;

	/**
	 * Used to load the jump information from file
	 * 
	 * @param sprite      the sprite which this movement is being applied to
	 * @param information the information ['maxJumpTicks,accelerationJump']
	 */
	public JumpMovement(Sprite sprite, String information) {
		super(sprite, information);
		String[] split = information.split(",");

		maxJumpTicks = Integer.parseInt(split[0]);
		accelerationJump = Double.parseDouble(split[1]);

	}

	/**
	 * Used to apply the jump movement
	 */
	@Override
	public void update(Vector velocity) {
		if (KeyboardManager.keyboardManager.isActive("player." + sprite.getPlayer() + ".up")) {
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
		if (sprite.getManager().level.isColliding(sprite.getShape(new Vector(0, -1)), sprite)) {
			ticksJumping = -1;
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
		velocity.applyVector(new Vector(0, accelerationJump), -1, sprite.getMaxSpeedY(), sprite.getMaxSpeed(), false);
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

	@Override
	public String getReference() {
		return reference;
	}

}
