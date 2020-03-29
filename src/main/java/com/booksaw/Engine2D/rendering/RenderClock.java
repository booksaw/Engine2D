package main.java.com.booksaw.Engine2D.rendering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * this class is used to manage the speed of rendering, at present, it will just
 * render the frame as quickly as possible (there is no cap)
 * 
 * @author booksaw
 *
 */
public class RenderClock implements ActionListener {

	private GameManager manager;
	private Timer timer;
	private boolean active = false;

	/**
	 * Used to create a new rendering clock
	 * 
	 * @param manager the game manager which the render clock is associated with
	 */
	public RenderClock(GameManager manager) {
		timer = new Timer(1, this);
		this.manager = manager;
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

	/**
	 * Where the repaint is called from
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// checking if the game is currently rendering
		if (manager.isRendering()) {
			Engine2DFrame.reRender();
		} else {
			// this should have been disabled, so disabling it manually
			setActive(false);
			// logging this automatic change
			Logger.Log(LogType.WARNING, "The render clock has been automatically disabled due to a possible error");
		}
	}

}
