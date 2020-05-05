package main.java.com.booksaw.Engine2D.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.Vector;

/**
 * A class to create a rectangle of solid color
 * 
 * @author booksaw
 *
 */
public class ColorObject extends GameObject {

	protected static String reference = "colorObject";

	public static String getReference() {
		return reference;
	}

	Color color;

	public ColorObject(GameManager manager, Element details) {
		super(manager, details);
		color = new Color(Integer.parseInt(Utils.getTagValue("rgb", details)));
	}

	@Override
	public void paint(Graphics graphics, GameManager manager, int x, int y, int width, int height) {
		graphics.setColor(color);
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

}
