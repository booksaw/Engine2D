package main.java.com.booksaw.Engine2D.objects;

import java.util.ArrayList;
import java.util.List;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.objects.movement.Movement;
import main.java.com.booksaw.Engine2D.rendering.animation.AnimationManager;

/**
 * @author nfgg2
 *
 */
public class Sprite extends ImageObject {

	public int player;
	private double accelerationJump = 3, maxJumpTicks = 20;
	public double maxSpeedX = 2.5, maxSpeedY = -1, maxSpeed = -1;
	private List<Movement> moveSet;

	public Sprite(AnimationManager manager, GameManager gameManager, int player) {
		super(manager, gameManager);
		this.player = player;
		moveSet = new ArrayList<>();
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

			for (Movement m : moveSet) {
				m.update(velocity);
			}

		}

	}

	/**
	 * Used to add a movement type to this sprite
	 * 
	 * @param m the movement type to add
	 */
	public void addMovement(Movement m) {
		moveSet.add(m);
	}

	/**
	 * Used to remove a movement type from this sprite
	 * 
	 * @param m the movement type to remove
	 */
	public void removeMovement(Movement m) {
		moveSet.remove(m);
	}

	/**
	 * Used to get a list of all for this sprite
	 * 
	 * @return the list
	 */
	public List<Movement> getMoveSet() {
		return moveSet;
	}

}
