package main.java.com.booksaw.Engine2D.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;

/**
 * This is the class which will control rendering for game windows
 * 
 * @author booksaw
 *
 */
public class RenderManager extends JPanel {

	private static final long serialVersionUID = 5739247288558689630L;

	/**
	 * The game manager which is controlling this render manager
	 */
	private GameManager manager;

	/**
	 * Used to store the game manager, so it can be accessed by all rendered
	 * components
	 * 
	 * @param manager the game manager associated with this object
	 */
	public RenderManager(GameManager manager) {
		this.manager = manager;
		// adding the keyboard manager
		addKeyListener(KeyboardManager.keyboardManager);
	}

	/**
	 * Used to store all components visible in the window
	 */
	List<RenderedComponent> components = new ArrayList<>();

	/**
	 * Used to add an element to the priority list
	 * 
	 * @param component the component to be added
	 * @param sort      if the list should be sorted, only use false if a lot of
	 *                  items are being added at the same time, and in this case run
	 *                  sortComponents() after all components have been added
	 */
	public void addComponent(RenderedComponent component, boolean sort) {
		components.add(component);
		if (sort) {
			sortComponents();
		}
	}

	/**
	 * Used to add a list of components to the priority list, the list does not need
	 * sortComponents() does not need running after, as it is automatically sorted
	 * 
	 * @param toAdd the list to add
	 */
	public void addComponents(List<RenderedComponent> toAdd) {
		components.addAll(toAdd);
		sortComponents();
	}

	/**
	 * Used to remove a rendered component
	 * 
	 * @param component the component to stop rendering
	 */
	public void removeComponent(RenderedComponent component) {
		components.remove(component);
	}

	/**
	 * Sorts the list of components by their priorities
	 */
	public void sortComponents() {
		Collections.sort(components);
	}

	/**
	 * This is where the rendering occurs
	 */
	@Override
	public void paint(Graphics g) {
		// rendering each component in turn
		g.setColor(Color.CYAN);
		g.fillRect(manager.camera.offsetX, manager.camera.offsetY, (int) manager.camera.width,
				(int) manager.camera.height);

		for (RenderedComponent component : new ArrayList<RenderedComponent>(components)) {
			component.paint(g, manager);
		}
		// adding black bars to cover up the offset (adding 5 for any slight rounding
		// error during calculations)
		g.setColor(Color.BLACK);
		if (manager.camera.offsetY != 0) {
			// top bar
			g.fillRect(0, 0, (int) (manager.camera.width + 5), (int) (manager.camera.offsetY));
			// bottom bar
			g.fillRect(0, (int) manager.camera.height + manager.camera.offsetY, (int) manager.camera.width + 5,
					(int) manager.camera.offsetY + 5);

		} else if (manager.camera.offsetX != 0) {
			// left bar
			g.fillRect(0, 0, manager.camera.offsetX, (int) manager.camera.height + 5);
			// right bar
			g.fillRect((int) manager.camera.width + manager.camera.offsetX, 0, manager.camera.offsetX + 5,
					(int) manager.camera.height + 5);
		}
	}
}
