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

	GameManager manager;
	int delay;

	public UpdateClock(int delay, GameManager manager) {
		super(delay);
		this.delay = delay;
		this.manager = manager;
	}

	long previousUpdate = -1;

	@Override
	public void setActive(boolean active) {
		super.setActive(active);
		if (active) {
			previousUpdate = System.currentTimeMillis();
		}
	}

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
