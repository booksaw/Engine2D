package main.java.com.booksaw.Engine2D.rendering;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import main.java.com.booksaw.Engine2D.CONFIG;
import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * This is used to manage the
 * 
 * @author booksaw
 *
 */
public class Engine2DFrame implements ComponentListener, WindowListener {

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
		gameFrame.addKeyListener(new KeyboardManager());

		// used to detect when the frame is resized
		gameFrame.addComponentListener(new Engine2DFrame());
		gameFrame.addWindowListener(new Engine2DFrame());
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

	public static void reRender() {
		gameFrame.repaint();
	}

	/**
	 * Used to store the game manager, used to notify the game manager about
	 * resizing and minimising
	 */
	private static GameManager gameManager;

	/**
	 * Used to set the render manager of the active game
	 * 
	 * @param manager the manager of the running game
	 */
	public static void setActiveRender(GameManager manager) {
		// adding the new game manager
		gameFrame.setContentPane(manager.getRenderManager());
		gameFrame.validate();
		// storing the manager
		gameManager = manager;

		Logger.Log(LogType.INFO, "Setting the rendering GameManager to " + manager);

	}

	/**
	 * Used to set the size of the JFrame
	 * 
	 * @param width  the new width of the jframe
	 * @param height the new height of the jframe
	 */
	public static void setSize(int width, int height) {

		gameFrame.setSize(width, height);

	}

	@Override
	public void componentResized(ComponentEvent e) {

		// checking if the camera is valid first
		if (gameManager != null && gameManager.camera != null) {
			Dimension dimension = gameFrame.getContentPane().getSize();
			gameManager.camera.resize(dimension.width, dimension.height);
			Logger.Log(LogType.INFO, "Window has been resized");
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-pause game?

	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		Logger.Log(LogType.INFO, "Program closing...");
		Logger.close(); 
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
