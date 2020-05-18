package main.java.com.booksaw.Engine2D.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.modifiers.type.ColorModifier;

/**
 * A class to create a rectangle of solid color
 * 
 * @author booksaw
 *
 */
public class ColorObject extends GameObject {

	/**
	 * The unique reference for this object type
	 */
	protected static String reference = "colorObject";

	/**
	 * Used to get the unique reference in a static way
	 * 
	 * @return the unique reference for this class
	 */
	public static String getStaticReference() {
		return reference;
	}

	/**
	 * Used to load a colorObject from file
	 * 
	 * @param manager the game manager which is creating the colorObject
	 * @param details the reference to the details about this gameObject
	 */
	public ColorObject(GameManager manager, Element details) {
		super(manager, details);
		getModifier("movable").setValue(false);
		addModifier(details, "rgb", "Object Color", new ColorModifier());

	}

	/**
	 * Used to create a new color object with default settings
	 * 
	 * @param manager the game manager which is creating the colorObject
	 */
	public ColorObject(GameManager manager) {
		super(manager);
		addModifier("rgb", "Object Color", Color.BLACK, new ColorModifier());
	}

	@Override
	public void paint(Graphics graphics, GameManager manager, int x, int y, int width, int height) {
		graphics.setColor(getColor());
		graphics.fillRect(x, y - height, width, height);
	}

	@Override
	public Shape getCollisionBox() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public Shape getCollisionBox(Vector translation) {
		return new Rectangle((int) (x + translation.x), (int) (y + translation.y), (int) width, (int) height);
	}

	@Override
	public String getReference() {
		return reference;
	}

	/**
	 * @return the color of the object (loaded from the modifier)
	 */
	private Color getColor() {
		return getModifier("rgb").getColorValue();
	}

}
