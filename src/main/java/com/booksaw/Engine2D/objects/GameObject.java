package main.java.com.booksaw.Engine2D.objects;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.w3c.dom.Document;
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
 * @author booksaw
 *
 */
public abstract class GameObject extends RenderedComponent implements Hitbox {

	public double x, y, startX, startY;
	public double width, height, startWidth, startHeight;
	public boolean movable = true;
	public double mass = 10;
	private GameObject collisionBottom, collisionLeft;
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
		startX = (Utils.getTagDouble("x", details));
		startY = (Utils.getTagDouble("y", details));
		x = startX;
		y = startY;

		width = (Utils.getTagDouble("width", details));
		height = (Utils.getTagDouble("height", details));
		startWidth = width;
		startHeight = height;

		movable = (Utils.getTagBoolean("movable", details));
		mass = (Utils.getTagDouble("mass", details));
		angle = (Utils.getTagDouble("angle", details));

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
		// the wrong place
		if (collisionBottom != null) {
			int collisionY = (int) (manager.camera.height
					- (((collisionBottom.y) - manager.camera.y) + collisionBottom.height) * manager.camera.scale)
					+ manager.camera.offsetY;
			renderedY = collisionY + 1;
		}

		if (collisionLeft != null) {
			int collisionX = (int) ((collisionLeft.x - manager.camera.x) * manager.camera.scale)
					+ manager.camera.offsetX;
			renderedX = collisionX + 1;
		}

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
		if ((velocity.x < 0 && incX > 0) || (velocity.x > 0 && incX < 0)) {
			incX = -incX;
		}
		if ((velocity.y < 0 && incY > 0) || (velocity.y > 0 && incY < 0)) {
			incY = -incY;
		}
		// used to move the object a small amount each time
		double tempx = 0, tempy = 0;
		for (int i = 0; i < mod; i++) {

			if (velocity.x != 0 && !manager.level.isColliding(getShape(new Vector(tempx + incX, tempy)), this)) {
				tempx += incX;
			} else {
				velocity.x = 0;
			}
			if (velocity.y != 0 && !manager.level.isColliding(getShape(new Vector(tempx, tempy + incY)), this)) {
				tempy += incY;
			} else {
				velocity.y = 0;
			}

		}
		// committing movements
		x += tempx;
		y += tempy;

		collisionBottom = manager.level.getColliding(getShape(new Vector(0, -1)), this);
		collisionLeft = manager.level.getColliding(getShape(new Vector(-1, 0)), this);
	}

	@Override
	public Shape getShape() {
		if (angle == 0) {
			return getCollisionBox();
		}
		AffineTransform tx = new AffineTransform();
		tx.rotate(angle, x + width / 2, y + height / 2);
		return tx.createTransformedShape(getCollisionBox());
	}

	@Override
	public Shape getShape(Vector translation) {
		if (angle == 0) {
			return getCollisionBox(translation);
		}
		AffineTransform tx = new AffineTransform();
		tx.rotate(angle, x + width / 2, y + height / 2);
		return tx.createTransformedShape(getCollisionBox(translation));
	}

	public GameManager getManager() {
		return manager;
	}

	public abstract Shape getCollisionBox();

	public abstract Shape getCollisionBox(Vector translation);

	/**
	 * This method is used to save all details about an object to file, when using,
	 * ensure you use super() to ensure all required data is saved
	 * 
	 * @param element
	 * @param document
	 */
	public void save(Element element, Document document) {
		Utils.saveValue("x", document, element, startX + "");
		Utils.saveValue("y", document, element, startY + "");
		Utils.saveValue("width", document, element, width + "");
		Utils.saveValue("height", document, element, height + "");
		Utils.saveValue("movable", document, element, movable + "");
		Utils.saveValue("angle", document, element, angle + "");
		Utils.saveValue("mass", document, element, mass + "");
		Utils.saveValue("type", document, element, getReference());

	}

	public abstract String getReference();

}
