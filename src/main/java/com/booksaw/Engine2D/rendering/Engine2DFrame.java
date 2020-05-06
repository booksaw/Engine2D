package main.java.com.booksaw.Engine2D.rendering;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;

import main.java.com.booksaw.Engine2D.CONFIG;
import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.modifiers.ModifierManager;

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
		Logger.Log(LogType.INFO, "Starting Engine2D Game frame");
		gameFrame = new JFrame();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setting the name of the frame to the determined one
		gameFrame.setTitle(CONFIG.NAME);
		gameFrame.setSize(width, height);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.addKeyListener(new KeyboardManager());
		KeyboardManager.keyboardManager.load(new File("platformer2D" + File.separator + "keymappings"));

		// used to detect when the frame is resized
		gameFrame.addComponentListener(new Engine2DFrame());
		gameFrame.addWindowListener(new Engine2DFrame());
	}

	/**
	 * Used to check if the frame is null
	 * 
	 * @return if the frame is ready
	 */
	public static boolean isInit() {
		return gameFrame != null;
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

		// ensuring the frame is the correct size
		gameFrame.getContentPane()
				.setPreferredSize(new Dimension(ModifierManager.getModifier("engine2d.frame.width").getIntValue(),
						ModifierManager.getModifier("engine2d.frame.height").getIntValue()));
		gameFrame.pack();
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
		// storing the window size

		Logger.Log(LogType.INFO, "Program closing...");
		Dimension d = gameFrame.getContentPane().getSize();
		ModifierManager.getModifier("engine2d.frame.height").value = d.height + "";
		ModifierManager.getModifier("engine2d.frame.width").value = d.width + "";
		ModifierManager.saveModifiers();

		// stopping the logger
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
