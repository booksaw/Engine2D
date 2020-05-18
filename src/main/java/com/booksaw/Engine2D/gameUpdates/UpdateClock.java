package main.java.com.booksaw.Engine2D.gameUpdates;

import java.awt.event.ActionEvent;

import main.java.com.booksaw.Engine2D.Clock;
import main.java.com.booksaw.Engine2D.GameManager;

/**
 * This is used to update the game 20 times a second, this should not be
 * associated with any rendering processes
 * 
 * @author booksaw
 *
 */
public class UpdateClock extends Clock {

	/**
	 * The game manager controlling the clock, used to tell it when to update
	 * objects
	 */
	GameManager manager;

	/**
	 * The delay of the clock (how many ms between update)
	 */
	int delay;

	/**
	 * Used to create a new update clock
	 * 
	 * @param delay   the delay of the clock (how many ms between each update)
	 * @param manager the gameManager which is controlling the clock
	 */
	public UpdateClock(int delay, GameManager manager) {
		super(delay);
		this.delay = delay;
		this.manager = manager;
	}

	/**
	 * This is used to track when the previous update occurred, so if there is a lag
	 * within the clock, it can be accounted for
	 */
	private transient long previousUpdate = -1;

	/**
	 * This method is used to start the update clock
	 */
	@Override
	public void setActive(boolean active) {
		super.setActive(active);
		if (active) {
			previousUpdate = System.currentTimeMillis();
		}
	}

	/**
	 * Detecting when the update needs to be run
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (previousUpdate == -1) {
			previousUpdate = System.currentTimeMillis();
			return;
		}

		int difference = (int) (System.currentTimeMillis() - previousUpdate);
		manager.update(difference / delay);

		previousUpdate = System.currentTimeMillis();

	}

}
