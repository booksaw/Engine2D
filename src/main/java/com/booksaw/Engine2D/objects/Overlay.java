package main.java.com.booksaw.Engine2D.objects;

import java.awt.Graphics;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.rendering.RenderedComponent;

/**
 * This class is used to display anything which is not placed within the level
 * (is not relative to the camera position)
 * 
 * @author booksaw
 *
 */
public abstract class Overlay extends RenderedComponent {

	/**
	 * The location of the component from the top left of the screen.
	 */
	int x, y;

	@Override
	public void paint(Graphics graphics, GameManager manager) {
		paint(graphics, manager, (int) (x * manager.camera.scale) + manager.camera.offsetX,
				(int) (y * manager.camera.scale) + manager.camera.offsetY);
	}

	/**
	 * This is used to paint the overlay
	 * 
	 * @param graphics the graphics to paint to
	 * @param manager  the manager which created the overlay
	 * @param x        the scaled x location of the overlay
	 * @param y        the scaled y location of the overlay
	 */
	public abstract void paint(Graphics graphics, GameManager manager, int x, int y);

}
