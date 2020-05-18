package main.java.com.booksaw.Engine2D;

import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * this class is used to manage the speed of rendering, at present, it will just
 * render the frame as quickly as possible (there is no cap)
 * 
 * @author booksaw
 *
 */
public abstract class Clock implements ActionListener {

	/**
	 * The timer which is being used to call the action
	 */
	private Timer timer;

	/**
	 * Stores if the timer is currently active
	 */
	private boolean active = false;

	/**
	 * Used to create a new rendering clock
	 * 
	 * @param manager the game manager which the render clock is associated with
	 */
	public Clock(int delay) {
		timer = new Timer(delay, this);
	}

	/**
	 * 
	 * @return if the rendering time is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets if the rendering timer is active or not
	 * 
	 * @param active if the timer should be active
	 */
	public void setActive(boolean active) {
		this.active = active;
		if (active) {
			timer.start();
		} else {
			timer.stop();
		}
	}

}
