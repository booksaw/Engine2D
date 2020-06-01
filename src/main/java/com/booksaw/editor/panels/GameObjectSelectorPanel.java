package main.java.com.booksaw.editor.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Level;
import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.objects.GameObject;
import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.SelectionManager;

public class GameObjectSelectorPanel extends Panel implements MouseListener {

	public GameObjectSelectorPanel(Panel parent) {
		super(parent);
	}

	@Override
	protected void createPanel(JPanel panel) {
		panel.setBackground(Constants.componentBackground);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		for (Entry<String, Class<?>> object : Level.getGameObjectTypes().entrySet()) {
			JPanel temp = new JPanel();
			BufferedImage image = Utils.loadTransparentImage(
					new File("Engine2D" + File.separator + "objects" + File.separator + object.getKey() + ".png"));

			if (image == null) {
				Logger.Log(LogType.WARNING,
						"Could not find specific image for " + object.getKey() + " using the default image");
				image = Utils.loadTransparentImage(
						new File("Engine2D" + File.separator + "objects" + File.separator + "gameObject.png"));
			}

			JLabel imageLabel = new JLabel(new ImageIcon(image));
			imageLabel.setMaximumSize(new Dimension(70, 70));
			imageLabel.setMinimumSize(new Dimension(70, 70));
			imageLabel.setPreferredSize(new Dimension(70, 70));

			temp.add(imageLabel);

			JLabel label = new JLabel(object.getKey());
			label.setForeground(Color.white);
			temp.add(label);

			temp.setSize(100, 100);
			temp.setPreferredSize(new Dimension(100, 100));
			temp.setBackground(Constants.componentBackground);
			temp.setName(object.getKey());
			addListeners(temp);
			panel.add(temp);

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// not associated with this class
		if (!(e.getSource() instanceof JPanel)) {
			return;
		}

		String name = ((JPanel) e.getSource()).getName();
		GameObject object = createObject(name, GamePanel.manager);
		GamePanel.manager.level.addObject(object);
		SelectionManager.select(object);

		GameObjectList.gameObjectList.update();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private GameObject createObject(String reference, GameManager manager) {
		Class<?> theClass = Level.getGameObjectTypes().get(reference);

		// an error has occurred
		Logger.Log("Creating Object: " + theClass);
		if (Level.getGameObjectTypes() == null) {
			return null;
		}

		try {
			Constructor<?> construct = theClass.getDeclaredConstructor(new Class[] { GameManager.class });
			GameObject object = (GameObject) construct.newInstance(manager);
			object.setStartX(GamePanel.manager.camera.x);
			object.reset();
			return object;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Logger.Log(LogType.ERROR, "Could not create an instance of the class: " + theClass);
			Logger.Log(LogType.ERROR, e.toString());
			Logger.Log(LogType.ERROR, e.getStackTrace() + "");
			return null;
		}
	}

}
