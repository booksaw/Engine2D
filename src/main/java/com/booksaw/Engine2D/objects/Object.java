package main.java.com.booksaw.Engine2D.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.collision.CollisionManager;
import main.java.com.booksaw.Engine2D.collision.Hitbox;
import main.java.com.booksaw.Engine2D.rendering.RenderedComponent;

/**
 * This is used for any objects within the world, this means the rendering call
 * (x and y) will be specific to the camera location.
 * 
 * @author nfgg2
 *
 */
public abstract class Object extends RenderedComponent implements Hitbox {

	public double x, y;
	public double width, height;
	public boolean movable = true;
	public double mass = 10;
	private Vector velocity;
	private GameManager manager;

	public Object(GameManager manager) {
		// TODO improved initialisation
		velocity = new Vector(1, 0);
		this.manager = manager;
	}

	// overriding method to call a more specific paint method
	@Override
	public void paint(Graphics graphics, GameManager manager) {
		// checking if it is on the camera
		if (!CollisionManager.isColliding(getShape(), manager.camera.getRectangle())) {
			return;
		}

		int renderedX = (int) ((x - manager.camera.x) * manager.camera.scale) + manager.camera.offsetX;
		// moving from y = 0 at the top of the screen to y = 0 being the bottom
		int renderedY = (int) (manager.camera.height - ((y - manager.camera.y) * manager.camera.scale))
				+ manager.camera.offsetY;

		int renderedWidth = (int) (width * manager.camera.scale);
		int renderedHeight = (int) (height * manager.camera.scale);

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

	/**
	 * Used to update the location of the object based on the velocity vector
	 * 
	 * @param time the time since last update
	 */
	public void updateLocation(int time) {

		double angle = velocity.getAngle();
		// rounding to the nearest whole number
		int mod = (int) (velocity.getMod() + 0.5);
		// checking if the object is stationary
		if (mod == 0) {
			return;
		}

		// checking the object is not starting by colliding
		if (manager.level.isColliding(getShape(), this)) {
			// no need to move the object
			return;
		}

		// pre-calculations to save efficiently
		double incX = Math.cos(angle), incY = Math.sin(angle);
		// used to move the object a small amount each time
		// TODO collisions with objects / floor stop momentum
		double tempx = 0, tempy = 0;
		for (int i = 0; i < mod; i++) {
			if (!manager.level.isColliding(getShape(new Vector(tempx + incX, tempy)), this)) {
				tempx += incX;
			}
			if (!manager.level.isColliding(getShape(new Vector(tempx, tempy + incY)), this)) {
				tempy += incY;
			}

		}
		// committing movements
		x += tempx;
		y += tempy;
	}

	@Override
	public Shape getShape() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public Shape getShape(Vector translation) {
		return new Rectangle((int) (x + translation.x), (int) (y + translation.y), (int) width, (int) height);
	}

}
