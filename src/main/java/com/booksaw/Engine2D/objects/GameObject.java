package main.java.com.booksaw.Engine2D.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.collision.CollisionManager;
import main.java.com.booksaw.Engine2D.collision.Hitbox;
import main.java.com.booksaw.Engine2D.modifiers.Modifier;
import main.java.com.booksaw.Engine2D.modifiers.type.BooleanModifier;
import main.java.com.booksaw.Engine2D.modifiers.type.DoubleModifier;
import main.java.com.booksaw.Engine2D.modifiers.type.ModifierType;
import main.java.com.booksaw.Engine2D.modifiers.type.ObjectIDModifier;
import main.java.com.booksaw.Engine2D.modifiers.type.RangedDoubleModifier;
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

	private HashMap<String, Modifier> modifiers = new HashMap<>();

	public transient double x, y;
	public transient double width, height;
	private transient GameObject collisionBottom, collisionLeft;
	protected Vector velocity;
	protected GameManager manager;

	public GameObject(GameManager manager) {
		// TODO improved initialisation
		velocity = new Vector(0, 0);
		this.manager = manager;
	}

	public Modifier addModifier(Element details, String reference, String description) {
		Modifier modifier = new Modifier(reference, description, details);
		modifiers.put(reference, modifier);
		return modifier;
	}

	public Modifier addModifier(Element details, String reference, String description, ModifierType type) {
		Modifier modifier = new Modifier(reference, description, details, type);
		modifiers.put(reference, modifier);
		return modifier;
	}

	public Modifier getModifier(String reference) {
		return modifiers.get(reference);
	}

	/**
	 * Used to get all modifiers for this object
	 * 
	 * @return a list of all modifiers
	 */
	public HashMap<String, Modifier> getModifiers() {
		return modifiers;
	}

	public GameObject(GameManager manager, Element details) {
		this.manager = manager;
		velocity = new Vector(0, 0);
		x = addModifier(details, "x", "X location", new DoubleModifier()).getDoubleValue();
		y = addModifier(details, "y", "Y location", new DoubleModifier()).getDoubleValue();

		width = addModifier(details, "width", "Width", new DoubleModifier()).getDoubleValue();
		height = addModifier(details, "height", "Height", new DoubleModifier()).getDoubleValue();

		addModifier(details, "movable", "Movable", new BooleanModifier());
		addModifier(details, "mass", "Mass", new DoubleModifier());
		addModifier(details, "angle", "Angle", new RangedDoubleModifier(0, Math.PI * 2));

		addModifier(details, "id", "Name", new ObjectIDModifier(manager, this));

	}

	public boolean isSelected = false;

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
		final double sin = Math.abs(Math.sin(getAngle()));
		final double cos = Math.abs(Math.cos(getAngle()));
		final int w = (int) Math.floor(renderedWidth * cos + renderedHeight * sin);
		final int h = (int) Math.floor(renderedHeight * cos + renderedWidth * sin);

		// calling the abstract method
		paint(graphics, manager, renderedX, renderedY, w, h);
		if (isSelected) {
			paintSelection(graphics, manager, renderedX, renderedY, w, h);
		}
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
		if (getAngle() == 0) {
			return getCollisionBox();
		}
		AffineTransform tx = new AffineTransform();
		tx.rotate(getAngle(), x + width / 2, y + height / 2);
		return tx.createTransformedShape(getCollisionBox());
	}

	@Override
	public Shape getShape(Vector translation) {
		if (getAngle() == 0) {
			return getCollisionBox(translation);
		}
		AffineTransform tx = new AffineTransform();
		tx.rotate(getAngle(), x + width / 2, y + height / 2);
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
		for (Entry<String, Modifier> modifier : modifiers.entrySet()) {
			modifier.getValue().Save(element, document);
		}
		Utils.saveValue("type", document, element, getReference());

	}

	public abstract String getReference();

	public void reset() {
		x = getModifier("x").getDoubleValue();
		y = getModifier("y").getDoubleValue();
		width = getModifier("width").getDoubleValue();
		height = getModifier("height").getDoubleValue();
		velocity = new Vector(0, 0);
	}

	/**
	 * Used to see if any pixel corrections are required
	 */
	public void checkCollisionOnReset() {
		collisionBottom = manager.level.getColliding(getShape(new Vector(0, -1)), this);
		collisionLeft = manager.level.getColliding(getShape(new Vector(-1, 0)), this);
	}

	/**
	 * Only use when initally making the ID
	 */
	private void generateID() {
		String ID = "";
		int i = 0;
		do {
			if (manager.level.getObject(getReference() + i, this) == null)
				ID = getReference() + i;
			i++;
		} while (ID == null || ID.equals(""));

		getModifier("id").setValue(ID);

	}

	@Override
	public String toString() {
		String ID = getID();
		if (ID == null || ID.equals("")) {
			generateID();
		}
		return ID;
	}

	public void paintSelection(Graphics g, GameManager manager, int x, int y, int width, int height) {

		final int circleR = 5;

		g.setColor(Color.WHITE);
		g.drawRect(x, y - height, width, height);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(x - circleR, (y - circleR), circleR * 2, circleR * 2);
		g.fillOval((x + width) - circleR, (y - circleR), circleR * 2, circleR * 2);
		g.fillOval(x - circleR, y - circleR - height, circleR * 2, circleR * 2);
		g.fillOval(x + width - circleR, y - circleR - height, circleR * 2, circleR * 2);

	}

	public String getID() {
		return getModifier("id").getStringValue();
	}

	public double getAngle() {
		return getModifier("angle").getDoubleValue();
	}

	public double getStartX() {
		return getModifier("x").getDoubleValue();
	}

	public void setStartX(double newX) {
		getModifier("x").setValue(newX);
	}

	public double getStartY() {
		return getModifier("y").getDoubleValue();
	}

	public void setStartY(double newY) {
		getModifier("y").setValue(newY);
	}
}