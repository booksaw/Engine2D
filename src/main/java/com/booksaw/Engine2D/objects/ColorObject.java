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

	protected static String reference = "colorObject";

	public static String getStaticReference() {
		return reference;
	}

	public ColorObject(GameManager manager, Element details) {
		super(manager, details);
		getModifier("movable").setValue(false);
		addModifier(details, "rgb", "Object Color", new ColorModifier());

	}

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

	private Color getColor() {
		return getModifier("rgb").getColorValue();
	}

}
