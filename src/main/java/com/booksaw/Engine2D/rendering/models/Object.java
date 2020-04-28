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

	int x, y, width, height;

	// overriding method to call a more specific paint method
	@Override
	public void paint(Graphics graphics, GameManager manager) {

		int renderedX = (int) ((x - manager.camera.x) * manager.camera.scale) + manager.camera.offsetX;
		// moving from y = 0 at the bottom of the screen to y = 0 being the top
		int renderedY = (int) (manager.camera.height - ((y - manager.camera.y) * manager.camera.scale))
				+ manager.camera.offsetY;

		int renderedWidth = (int) (width * manager.camera.scale);
		int renderedHeight = (int) (height * manager.camera.scale);

		// TODO only render the object if it is on the camera

		// calling the abstract method
		paint(graphics, manager, renderedX, renderedY, renderedWidth, renderedHeight);
	}

	/**
	 * Where the object should be rendered, use the included x and y as they will be
	 * relative to the camera
	 * 
	 * @param graphics the graphics of the window
	 * @param manager  the game manager of the game this object is controlled by
	 * @param x        the x coord the object should be rendered at
	 * @param y        the y coord the object should be rendered at
	 * @param width    the width of the object after scaling
	 * @param height   the height of the object after scaling
	 */
	public abstract void paint(Graphics graphics, GameManager manager, int x, int y, int width, int height);

}
