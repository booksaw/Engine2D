package main.java.com.booksaw.Engine2D.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.collision.CollisionManager;
import main.java.com.booksaw.Engine2D.collision.Hitbox;
import main.java.com.booksaw.Engine2D.logging.Logger;
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

	/**
	 * This is used to store a list of modifiers, any value which needs storing to
	 * file should be put in here instead of in a varaible
	 */
	private HashMap<String, Modifier> modifiers = new HashMap<>();

	/**
	 * The current x and y location of the object
	 */
	public transient double x, y;

	/**
	 * The current width and height of the object
	 */
	public transient double width, height;

	/**
	 * Used to store the object that is colliding below and to the left, this is
	 * used to fix any rounding problems when scaling in the rendering process
	 */
	private transient GameObject collisionBottom, collisionLeft;

	/**
	 * The velocity of the object, unit is pixels per tick
	 */
	protected Vector velocity;

	/**
	 * The game manager which created this object
	 */
	protected GameManager manager;

	/**
	 * Used to create a default object at 0,0, this is used when a new object is
	 * created
	 * 
	 * @param manager the game manager which is creating the object
	 */
	public GameObject(GameManager manager) {
		velocity = new Vector(0, 0);
		this.manager = manager;

		addModifier("id", "Name", "", new ObjectIDModifier(manager, this));
		generateID();
		// setting default values
		x = addModifier("x", "X location", 0, new DoubleModifier()).getDoubleValue();
		y = addModifier("y", "Y locaiton", 0, new DoubleModifier()).getDoubleValue();
		width = addModifier("width", "Width", 100, new DoubleModifier()).getDoubleValue();
		height = addModifier("height", "Height", 100, new DoubleModifier()).getDoubleValue();
		addModifier("movable", "Movable", true, new BooleanModifier());
		addModifier("mass", "Mass", 10, new DoubleModifier());
		addModifier("angle", "Angle", 0, new RangedDoubleModifier(0, Math.PI * 2));
	}

	/**
	 * This constructor is used when loading a gameObject frame file
	 * 
	 * @param manager the gameManager which is creating the object
	 * @param details the details about the object (the XML element)
	 */
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

	/**
	 * This is used to add a new modifier to the list of modifiers
	 * 
	 * @param details     where the value of this modifier is saved
	 * @param reference   the internal reference of the modifier
	 * @param description the description of the modifier
	 * @return the modifier, once it has been created
	 */
	public Modifier addModifier(Element details, String reference, String description) {
		Modifier modifier = new Modifier(reference, description, details);
		modifiers.put(reference, modifier);
		return modifier;
	}

	/**
	 * preferred method of doing it, This is used to add a new modifier to the list
	 * of modifiers
	 * 
	 * @param details     where the value of this modifier is saved
	 * @param reference   the internal reference of the modifier
	 * @param description the description of the modifier
	 * @param type        the type of the modifier, this is used when displaying the
	 *                    modifier in the editor
	 * @return the modifier, once it has been created
	 */
	public Modifier addModifier(Element details, String reference, String description, ModifierType type) {
		Modifier modifier = new Modifier(reference, description, details, type);
		modifiers.put(reference, modifier);
		return modifier;
	}

	/**
	 * This is used to add a modifier to the list which is not saved to file
	 * 
	 * @param reference   the internal reference of the modifier
	 * @param description the description of the modifier
	 * @param value       the initial value of the modifier
	 * @param type        the type of the modifier, this is used when displaying the
	 *                    modifier in the editor
	 * @return
	 */
	public Modifier addModifier(String reference, String description, Object value, ModifierType type) {
		Modifier modifier = new Modifier(reference, value, description, type);
		modifiers.put(reference, modifier);
		return modifier;
	}

	/**
	 * @param reference the internal reference of the modifier
	 * @return the modifier with that reference
	 */
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

	/**
	 * Used to store if the object is selected
	 */
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

	/**
	 * @return The game manager which created this gameObject
	 */
	public GameManager getManager() {
		return manager;
	}

	/**
	 * @return The collision box of the shape in its current location
	 */
	public abstract Shape getCollisionBox();

	/**
	 * Used to get the collision box of a hypothesised change (used to check
	 * collision of the change before the change is carried out)
	 * 
	 * @param translation The translation that the object may take (add transation.x
	 *                    onto x, transation.y onto y)
	 * @return the collision box of the shape in the proposed location
	 */
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

	/**
	 * Used to get the unique reference of this gameObject type
	 * 
	 * @return
	 */
	public abstract String getReference();

	/**
	 * Used to reset the object to its initial location and modifiers, this is done
	 * by loading the modifiers into variables
	 */
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
	 * Only use when initially making the ID
	 */
	private void generateID() {
		String ID = "";
		int i = 0;
		do {
			Logger.Log(getReference() + i + " = " + manager.level.getObject(getReference() + i, this));
			if (manager.level.getObject(getReference() + i, this) == null) {
				ID = getReference() + i;
			}
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

	/**
	 * The radius of the selection circles (in the cornders of the selection
	 */
	public static final int circleR = 5;

	/**
	 * Used to display the text box style movement controls around the objects
	 * hitbox
	 * 
	 * @param g       the graphics to draw to
	 * @param manager the
	 * @param x       the scaled x value
	 * @param y       the scaled y value
	 * @param width   the width of the box
	 * @param height  the height of the box
	 */
	public void paintSelection(Graphics g, GameManager manager, int x, int y, int width, int height) {

		g.setColor(Color.WHITE);
		g.drawRect(x, y - height, width, height);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(x - circleR, (y - circleR), circleR * 2, circleR * 2);
		g.fillOval((x + width) - circleR, (y - circleR), circleR * 2, circleR * 2);
		g.fillOval(x - circleR, y - circleR - height, circleR * 2, circleR * 2);
		g.fillOval(x + width - circleR, y - circleR - height, circleR * 2, circleR * 2);

	}

	/**
	 * @return the ID of this gameObject
	 */
	public String getID() {
		return getModifier("id").getStringValue();
	}

	/**
	 * @return The angle of the game object
	 */
	public double getAngle() {
		return getModifier("angle").getDoubleValue();
	}

	/**
	 * @return the start x location of this object
	 */
	public double getStartX() {
		return getModifier("x").getDoubleValue();
	}

	/**
	 * @param newX the new start x location of this object
	 */
	public void setStartX(double newX) {
		getModifier("x").setValue(newX);
	}

	/**
	 * @return the start y location of this object
	 */
	public double getStartY() {
		return getModifier("y").getDoubleValue();
	}

	/**
	 * @param newY the new start y location of this object
	 */
	public void setStartY(double newY) {
		getModifier("y").setValue(newY);
	}

	/**
	 * @return The start width of this object
	 */
	public double getStartWidth() {
		return getModifier("width").getDoubleValue();
	}

	/**
	 * @param newWidth the new start width of this object
	 */
	public void setStartWidth(double newWidth) {
		getModifier("width").setValue(newWidth);
	}

	/**
	 * @return the start height of this object
	 */
	public double getStartHeight() {
		return getModifier("height").getDoubleValue();
	}

	/**
	 * @param newHeight the new start height of this object
	 */
	public void setStartHeight(double newHeight) {
		getModifier("height").setValue(newHeight);
	}

	/**
	 * @return the logo image for the editor
	 */
	public BufferedImage getImage() {
		return Utils.loadTransparentImage(new File("Engine2D" + File.separator + "gameObject.png"));
	}
}