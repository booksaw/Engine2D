package main.java.com.booksaw.Engine2D.rendering;

import java.awt.event.ActionEvent;

import main.java.com.booksaw.Engine2D.Clock;
import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.editor.panels.GamePanel;

/**
 * this class is used to manage the speed of rendering, at present, it will just
 * render the frame as quickly as possible (there is no cap)
 * 
 * @author booksaw
 *
 */
public class RenderClock extends Clock {

	private GameManager manager;

	/**
	 * Used to create a new rendering clock
	 * 
	 * @param manager the game manager which the render clock is associated with
	 */
	public RenderClock(GameManager manager) {
		super(1);
		this.manager = manager;
	}

	/**
	 * Where the repaint is called from
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// checking if the game is currently rendering
		if (manager.isRendering()) {
			if (Engine2DFrame.isInit()) {
				Engine2DFrame.reRender();
			} else if (GamePanel.isInit()) {
				GamePanel.repaintAll();
			}
		} else {
			// this should have been disabled, so disabling it manually
			setActive(false);
			// logging this automatic change
			Logger.Log(LogType.WARNING, "The render clock has been automatically disabled due to a possible error");
		}
	}

}
