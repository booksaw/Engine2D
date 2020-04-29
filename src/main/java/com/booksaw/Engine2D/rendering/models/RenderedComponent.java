package main.java.com.booksaw.Engine2D.rendering.models;

import java.awt.Graphics;

import main.java.com.booksaw.Engine2D.GameManager;

/**
 * This class is used for all objects which are drawn, this means that the
 * render code will be called within them. Comparable is implemented to make
 * sorting of the priority list, much simpler. NOTE: It is not recommended to
 * ever use this class, but instead use a sub class, as this class does not take
 * into account the offset of the camera. Anything outside of the camera offset
 * will be hidden!!
 * 
 * @author booksaw
 *
 */
public abstract class RenderedComponent implements Comparable<RenderedComponent> {

	/**
	 * This is the priority at which the object is rendered. 0-99, the lower the
	 * priority the earlier it is rendered (higher priority brings items to the
	 * front)
	 */
	private int priority = 1;

	/**
	 * The most basic render code, more specific versions exist within the
	 * com.booksaw.Engine2D.rendering package
	 * 
	 * @param graphics the graphics component of the window
	 */
	public abstract void paint(Graphics graphics, GameManager manager);

	/**
	 * Simple getter method to get the priority of the rendered component
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Used when ordering the priority of components
	 */
	@Override
	public int compareTo(RenderedComponent o) {
		return priority < o.getPriority() ? -1 : priority > o.getPriority() ? 1 : 0;
	}

}
