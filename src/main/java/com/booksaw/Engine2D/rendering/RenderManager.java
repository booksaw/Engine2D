package main.java.com.booksaw.Engine2D.rendering;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.rendering.models.RenderedComponent;

/**
 * This is the class which will control rendering for game windows
 * 
 * @author booksaw
 *
 */
public class RenderManager extends JPanel {

	private static final long serialVersionUID = 5739247288558689630L;

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
		for (RenderedComponent component : components) {
			component.paint(g, manager);
		}

	}
}
