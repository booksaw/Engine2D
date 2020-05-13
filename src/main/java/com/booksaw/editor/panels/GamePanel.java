package main.java.com.booksaw.editor.panels;

import java.awt.Cursor;
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
import main.java.com.booksaw.editor.CameraController;
import main.java.com.booksaw.editor.SelectionManager;
import main.java.com.booksaw.editor.mouse.MouseFunction;

public class GamePanel extends Panel implements ComponentListener, MouseListener, MouseMotionListener {

	public static boolean grid = false;

	private static final int gridWidth = 10;

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
	private CameraController controller;

	public static GameManager manager;

	public GamePanel(GameManager manager, Panel parent) {
		super(parent);
		GamePanel.manager = manager;
		controller = new CameraController(manager);
		panels.add(this);
		manager.pause(false);
	}

	Cursor defaultCursor;

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

		defaultCursor = panel.getCursor();

		// storing the manager
		Logger.Log(LogType.INFO, "Setting the rendering GameManager to " + manager);

	}

	public void repaint() {
		panel.repaint();
	}

	public void setGameManager() {
		panel.removeAll();
		panel.add(manager.getRenderManager());
		controller.remove();
		controller = new CameraController(manager);
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
				Logger.Log(LogType.INFO, "Object selected: " + o);

				SelectionManager.select(o);
			}

		} else if (MouseFunction.GENERAL == MouseFunction.activeFunction) {
			Point p = e.getPoint();
			p.x = (int) (((p.x - manager.camera.offsetX) / manager.camera.scale) + manager.camera.x);
			p.y = (int) (((manager.camera.height - (p.y + manager.camera.offsetY)) / manager.camera.scale)
					+ manager.camera.x);
			GameObject o = manager.level.getColliding(new Rectangle(p, new Dimension(1, 1)), null);
			if (o == null) {
				// resetting the mouse function
				SelectionManager.clearSelection();
			} else {
				Logger.Log(LogType.INFO, "Object selected: " + o);
				SelectionManager.select(o);
			}
		}
	}

	double startx, starty;
	private boolean dragged = false;

	@Override
	public void mousePressed(MouseEvent e) {

		panel.getParent().dispatchEvent(e);

		Point start = e.getPoint();
		startx = (int) (((start.x - manager.camera.offsetX) / manager.camera.scale) + manager.camera.x);
		starty = (int) (((manager.camera.height - (start.y + manager.camera.offsetY)) / manager.camera.scale)
				+ manager.camera.x);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (dragged) {
			for (GameObject o : SelectionManager.getSelected()) {
				o.setStartX(o.x);
				o.setStartY(o.y);
				o.setStartWidth(o.width);
				o.setStartHeight(o.height);
				o.reset();
				dragged = false;
			}
		}

		ObjectModifierPanel.modifierPanel.update();
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

		Point p = e.getPoint();
		double px = (int) (((p.x - manager.camera.offsetX) / manager.camera.scale) + manager.camera.x);
		double py = (int) (((manager.camera.height - (p.y + manager.camera.offsetY)) / manager.camera.scale)
				+ manager.camera.x);
		if (panel.getCursor().getType() == defaultCursor.getType()) {
			panel.getParent().dispatchEvent(e);
			return;
		}

		if (!dragged) {
			for (GameObject o : SelectionManager.getSelected()) {
				o.reset();
			}
		}

		switch (panel.getCursor().getType()) {
		case Cursor.MOVE_CURSOR:
			// calculating the distance it has been moved
			for (GameObject o : SelectionManager.getSelected()) {
				o.x = (o.getStartX() + (px - startx));
				o.y = (o.getStartY() + (py - starty));

				ObjectModifierPanel.modifierPanel.update();
			}
			break;
		case Cursor.NE_RESIZE_CURSOR:
			for (GameObject o : SelectionManager.getSelected()) {
				o.width = (o.getStartWidth() + (px - startx));
				o.height = (o.getStartHeight() + (py - starty));

				ObjectModifierPanel.modifierPanel.update();
			}
			break;
		case Cursor.SW_RESIZE_CURSOR:
			for (GameObject o : SelectionManager.getSelected()) {
				o.width = (o.getStartWidth() - (px - startx));
				o.x = (o.getStartX() + (px - startx));

				o.height = (o.getStartHeight() - (py - starty));
				o.y = (o.getStartY() + (py - starty));
			}
			break;
		case Cursor.NW_RESIZE_CURSOR:
			for (GameObject o : SelectionManager.getSelected()) {
				o.width = (o.getStartWidth() - (px - startx));
				o.x = (o.getStartX() + (px - startx));

				o.height = (o.getStartHeight() + (py - starty));
			}
			break;
		case Cursor.SE_RESIZE_CURSOR:
			for (GameObject o : SelectionManager.getSelected()) {
				o.width = (o.getStartWidth() + (px - startx));

				o.height = (o.getStartHeight() - (py - starty));
				o.y = (o.getStartY() + (py - starty));
			}
			break;
		case Cursor.N_RESIZE_CURSOR:
			for (GameObject o : SelectionManager.getSelected()) {
				o.height = (o.getStartHeight() + (py - starty));
			}
			break;
		case Cursor.S_RESIZE_CURSOR:
			for (GameObject o : SelectionManager.getSelected()) {
				o.height = (o.getStartHeight() - (py - starty));
				o.y = (o.getStartY() + (py - starty));
			}
			break;
		case Cursor.E_RESIZE_CURSOR:
			for (GameObject o : SelectionManager.getSelected()) {
				o.width = (o.getStartWidth() + (px - startx));
			}
			break;
		case Cursor.W_RESIZE_CURSOR:
			for (GameObject o : SelectionManager.getSelected()) {
				o.width = (o.getStartWidth() - (px - startx));
				o.x = (o.getStartX() + (px - startx));
			}
			break;
		}

		if (grid) {
			for (GameObject o : SelectionManager.getSelected()) {
				o.width -= (o.width - gridWidth) % gridWidth;
				o.height -= (o.height - gridWidth) % gridWidth;
				if (o.width < gridWidth) {
					o.width = gridWidth;
				}
				if (o.height < gridWidth) {
					o.height = gridWidth;
				}
				o.x -= (o.x - gridWidth) % gridWidth;
				o.y -= (o.y - gridWidth) % gridWidth;
			}
		} else {
			// ensuring width is not negative
			for (GameObject o : SelectionManager.getSelected()) {
				if (o.width <= 0) {
					o.width = 1;
				}
				if (o.height <= 0) {
					o.height = 1;
				}
			}
		}

		dragged = true;

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		Point p = e.getPoint();
		p.x = (int) (((p.x - manager.camera.offsetX) / manager.camera.scale) + manager.camera.x);
		p.y = (int) (((manager.camera.height - (p.y + manager.camera.offsetY)) / manager.camera.scale)
				+ manager.camera.x);
		Rectangle cursor = new Rectangle(p, new Dimension(1, 1));
		GameObject o = manager.level.getColliding(cursor, null);

		if (o == null || !o.isSelected) {
			panel.setCursor(defaultCursor);
			panel.getParent().dispatchEvent(e);
			return;
		}

		// moving the cursor to the locaiton inside the object
		cursor.x = (int) (cursor.x - o.x);
		cursor.y = (int) (cursor.y - o.y);

		// finding what the cursor should look like
		if (cursor.x < GameObject.circleR * 2 && cursor.y < GameObject.circleR * 2) {
			panel.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
		} else if (cursor.x < GameObject.circleR * 2 && cursor.y > o.height - GameObject.circleR * 2) {
			panel.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
		} else if (cursor.x > o.width - GameObject.circleR * 2 && cursor.y < GameObject.circleR * 2) {
			panel.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		} else if (cursor.x > o.width - GameObject.circleR * 2 && cursor.y > o.height - GameObject.circleR * 2) {
			panel.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
		} else if (cursor.x < GameObject.circleR * 2) {
			panel.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
		} else if (cursor.x > o.width - GameObject.circleR * 2) {
			panel.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		} else if (cursor.y < GameObject.circleR * 2) {
			panel.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		} else if (cursor.y > o.height - GameObject.circleR * 2) {
			panel.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		} else {
			panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		}

	}

}
