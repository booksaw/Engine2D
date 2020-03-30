package main.java.com.booksaw.Engine2D.rendering.models;

import java.awt.Graphics;

import main.java.com.booksaw.Engine2D.GameManager;

/**
 * This is used for any objects within the world, this means the rendering call
 * (x and y) will be specific to the camera location.
 * 
 * @author nfgg2
 *
 */
public abstract class Object extends RenderedComponent {

	int x, y;

	// overriding method to call a more specific paint method
	@Override
	public void paint(Graphics graphics, GameManager manager) {

		int renderedX = x - manager.camera.x;
		// moving from y = 0 at the bottom of the screen to y = 0 being the top
		int renderedY = manager.camera.height - (y - manager.camera.y);

		// TODO only render the object if it is on the camera

		// calling the abstract method
		paint(graphics, manager, renderedX, renderedY);
	}

	/**
	 * Where the object should be rendered, use the included x and y as they will be
	 * relative to the camera
	 * 
	 * @param graphics the graphics of the window
	 * @param manager  the game manager of the game this object is controlled by
	 * @param x        the x coord the object should be rendered at
	 * @param y        the y coord the object should be rendered at
	 */
	public abstract void paint(Graphics graphics, GameManager manager, int x, int y);

}
