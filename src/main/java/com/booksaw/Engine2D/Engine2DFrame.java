package main.java.com.booksaw.Engine2D;

import javax.swing.JFrame;

import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * This is used to manage the
 * 
 * @author booksaw
 *
 */
public class Engine2DFrame {

	/**
	 * The game is contained within this JFrame
	 */
	private static JFrame gameFrame;

	/**
	 * Used to initiate the frame, this should be run before the frame is displayed
	 * to correctly set-up
	 * 
	 * @param width  the starting width of the panel
	 * @param height the starting height of the panel
	 */
	public static void initFrame(int width, int height) {

		gameFrame = new JFrame();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting the name of the frame to the determined one
		gameFrame.setName(CONFIG.NAME);
		gameFrame.setSize(width, height);
		gameFrame.setLocationRelativeTo(null);

	}

	/**
	 * Used to display the frame (used after all configuration has occurred)
	 * 
	 * @param show if the window should be displayed or hidden
	 */
	public static void setVisible(boolean show) {

		// if initFrame() has not been run
		if (gameFrame == null) {
			Logger.Log(LogType.ERROR, "Frame cannot be displayed before it has been initilized");
			return;
		}

		// displaying the frame
		gameFrame.setVisible(show);

	}

}
