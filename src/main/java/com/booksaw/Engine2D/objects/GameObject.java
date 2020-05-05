package main.java.com.booksaw.Engine2D.objects;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.collision.CollisionManager;
import main.java.com.booksaw.Engine2D.collision.Hitbox;
import main.java.com.booksaw.Engine2D.rendering.RenderedComponent;

/**
 * This is used for any objects within the world, this means the rendering call
 * (x and y) will be specific to the camera location. NOTE: do not forget to set
 * the unique saving reference
 * 
 * @author nfgg2
 *
 */
public abstract class GameObject extends RenderedComponent implements Hitbox {

	public double x, y, startX, startY;
	public double width, height, startWidth, startHeight;
	public boolean movable = true;
	public double mass = 10;
	/**
	 * The angle that the object has been rotated, keep between 0 and 2 pi
	 */
	public double angle = 0;
	protected Vector velocity;
	protected GameManager manager;

	public GameObject(GameManager manager) {
		// TODO improved initialisation
		velocity = new Vector(0, 0);
		this.manager = manager;
	}

	public GameObject(GameManager manager, Element details) {
		this.manager = manager;
		velocity = new Vector(0, 0);
		startX = Double.parseDouble(Utils.getTagValue("x", details));
		startY = Double.parseDouble(Utils.getTagValue("y", details));
		x = startX;
		y = startY;

		width = Double.parseDouble(Utils.getTagValue("width", details));
		height = Double.parseDouble(Utils.getTagValue("height", details));
		startWidth = width;
		startHeight = height;
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
		final double sin = Math.abs(Math.sin(angle));
		final double cos = Math.abs(Math.cos(angle));
		final int w = (int) Math.floor(renderedWidth * cos + renderedHeight * sin);
		final int h = (int) Math.floor(renderedHeight * cos + renderedWidth * sin);

		// calling the abstract method
		paint(graphics, manager, renderedX, renderedY, w, h);
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
		angle += 0.01;
		if (angle > 2 * Math.PI) {
			angle = angle % (Math.PI * 2);
		}
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
		// checking for negatives
		if (velocity.x < 0) {
			incX = -incX;
		}

		// used to move the object a small amount each time
		// TODO collisions with objects / floor stop momentum
		double tempx = 0, tempy = 0;
		for (int i = 0; i < mod; i++) {

			if (!manager.level.isColliding(getShape(new Vector(tempx + incX, tempy)), this) && velocity.x != 0) {
				tempx += incX;
			} else {
				velocity.x = 0;
			}
			if (!manager.level.isColliding(getShape(new Vector(tempx, tempy + incY)), this) && velocity.y != 0) {
				tempy += incY;
			} else {
				velocity.y = 0;
			}

		}
		// committing movements
		x += tempx;
		y += tempy;
	}

	@Override
	public Shape getShape() {
		AffineTransform tx = new AffineTransform();
		tx.rotate(angle, x + width / 2, y + height / 2);
		return tx.createTransformedShape(getCollisionBox());
	}

	@Override
	public Shape getShape(Vector translation) {
		AffineTransform tx = new AffineTransform();
		tx.rotate(angle, x + width / 2, y + height / 2);
		return tx.createTransformedShape(getCollisionBox(translation));
	}

	public GameManager getManager() {
		return manager;
	}

	public abstract Shape getCollisionBox();

	public abstract Shape getCollisionBox(Vector translation);

}
