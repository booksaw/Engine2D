package main.java.com.booksaw.editor.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.objects.GameObject;
import main.java.com.booksaw.editor.mouse.MouseFunction;

public class GamePanel extends Panel implements ComponentListener, MouseListener, MouseMotionListener {

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
		GamePanel.manager = manager;

		for (GamePanel panel : panels) {
			panel.setGameManager();
		}
		manager.pause(false);
	}

	public static List<GamePanel> panels = new ArrayList<>();
	public static GameObject selectedObject = null;

	public static GameManager manager;

	public GamePanel(GameManager manager, Panel parent) {
		super(parent);
		GamePanel.manager = manager;
		panels.add(this);
		manager.pause(false);
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
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		// ensuring the frame is the correct size
		panel.validate();

		// storing the manager
		Logger.Log(LogType.INFO, "Setting the rendering GameManager to " + manager);

	}

	public void repaint() {
		panel.repaint();
	}

	public void setGameManager() {
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

	@Override
	public void mouseClicked(MouseEvent e) {

		panel.getParent().dispatchEvent(e);

		if (MouseFunction.OBJECTSELECT == MouseFunction.activeFunction) {
			Point p = e.getPoint();
			p.x = (int) (((p.x - manager.camera.offsetX) / manager.camera.scale) + manager.camera.x);
			p.y = (int) (((manager.camera.height - (p.y + manager.camera.offsetY)) / manager.camera.scale)
					+ manager.camera.x);
			GameObject o = manager.level.getColliding(new Rectangle(p, new Dimension(1, 1)), null);
			if (o == null) {
				// resetting the mouse function
				MouseFunction.activeFunction = MouseFunction.GENERAL;
			} else {
				if (selectedObject != null) {

					selectedObject.isSelected = false;
				}
				Logger.Log(LogType.INFO, "Object selected: " + o);

				selectedObject = o;
				selectedObject.isSelected = true;
			}

		} else if (MouseFunction.GENERAL == MouseFunction.activeFunction) {
			Point p = e.getPoint();
			p.x = (int) (((p.x - manager.camera.offsetX) / manager.camera.scale) + manager.camera.x);
			p.y = (int) (((manager.camera.height - (p.y + manager.camera.offsetY)) / manager.camera.scale)
					+ manager.camera.x);
			GameObject o = manager.level.getColliding(new Rectangle(p, new Dimension(1, 1)), null);
			if (o == null) {
				// resetting the mouse function
				MouseFunction.activeFunction = MouseFunction.GENERAL;
			} else {
				if (selectedObject != null) {

					selectedObject.isSelected = false;
				}
				Logger.Log(LogType.INFO, "Object selected: " + o);

				selectedObject = o;
				selectedObject.isSelected = true;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		panel.getParent().dispatchEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		panel.getParent().dispatchEvent(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		panel.getParent().dispatchEvent(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {

		panel.getParent().dispatchEvent(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		panel.getParent().dispatchEvent(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		panel.getParent().dispatchEvent(e);
	}

}
