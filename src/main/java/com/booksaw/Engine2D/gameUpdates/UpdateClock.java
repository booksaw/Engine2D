package main.java.com.booksaw.Engine2D.gameUpdates;

import java.awt.event.ActionEvent;

import main.java.com.booksaw.Engine2D.Clock;

/**
 * This is used to update the game 20 times a second, this should not be
 * associated with any rendering processes
 * 
 * @author booksaw
 *
 */
public class UpdateClock extends Clock {

	public UpdateClock(int delay) {
		super(delay);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODOf
	}

}
