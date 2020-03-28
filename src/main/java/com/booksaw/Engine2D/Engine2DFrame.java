package main.java.com.booksaw.Engine2D;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * This is used to manage the
 * 
 * @author nfgg2
 *
 */
public class Engine2DFrame {

	/**
	 * TThe game is contained within this JFrame
	 */
	private static JFrame gameFrame;

	/**
	 * Used to initiate the frame, this should be run before the frame is displayed
	 * to correctly set-up
	 * 
	 * @param startingDimension the starting dimension of the panel
	 */
	public static void initFrame(Dimension startingDimension) {

		gameFrame = new JFrame();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting the name of the frame to the determined one
		gameFrame.setName(CONFIG.NAME);
		gameFrame.setSize(startingDimension);
		gameFrame.setLocationRelativeTo(null);

	}

}
