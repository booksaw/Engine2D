package main.java.com.booksaw.editor.panels;

import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

public class GamePanel extends Panel implements ComponentListener {

	/**
	 * Used to check if the panels are ready
	 * 
	 * @return if the panels are readyF
	 */
	public static boolean isInit() {
		if (panels.size() != 0) {
			return true;
		}
		return false;
	}

	public static void repaintAll() {
		for (GamePanel panel : panels) {
			panel.repaint();
		}
	}

	public static void setActiveRender(GameManager manager) {
		for (GamePanel panel : panels) {
			panel.setGameManager(manager);
		}
	}

	private static List<GamePanel> panels = new ArrayList<>();;

	GameManager manager;

	public GamePanel(GameManager manager) {
		this.manager = manager;
		panels.add(this);
	}

	@Override
	protected void createPanel(JPanel panel) {
		// adding the new game manager
		panel.addComponentListener(this);
		panel.setLayout(new GridLayout());
		panel.add(manager.getRenderManager());
		manager.setRendering(true);
		manager.camera.resize(panel.getWidth(), panel.getHeight());
		manager.resume();

		// ensuring the frame is the correct size
		panel.validate();

		// storing the manager
		Logger.Log(LogType.INFO, "Setting the rendering GameManager to " + manager);

	}

	public void repaint() {
		panel.repaint();
		panel.getComponents()[0].repaint();
	}

	public void setGameManager(GameManager manager) {
		this.manager = manager;
		panel.removeAll();
		panel.add(manager.getRenderManager());
	}

	@Override
	public void componentResized(ComponentEvent e) {
		manager.camera.resize(panel.getWidth(), panel.getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

}
