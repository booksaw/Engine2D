package main.java.com.booksaw.Engine2D.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.Engine2D.rendering.animation.AnimationManager;

/**
 * Less primitive class, used to display objects which have a location somewhere
 * in the level
 * 
 * @author booksaw
 *
 */
public class ImageObject extends GameObject implements Updateable {

	/**
	 * The unique reference for this object type
	 */
	protected static String reference = "imageObject";

	/**
	 * Used to get the unique reference in a static way
	 * 
	 * @return the unique reference for this class
	 */
	public static String getStaticReference() {
		return reference;
	}

	/**
	 * The animation manager which controls the image rendering
	 */
	protected AnimationManager animationManager;

	/**
	 * Used to load a imageObject from file
	 * 
	 * @param manager the game manager which is creating the imageObject
	 * @param details the reference to the details about this gameObject
	 */
	public ImageObject(GameManager gameManager, Element details) {
		super(gameManager, details);

		// loading the animation details from xml
		animationManager = new AnimationManager(details);
		gameManager.addUpdatable(this);

	}

	/**
	 * Used to create a new image object with default settings
	 * 
	 * @param manager the game manager which is creating the colorObject
	 */
	public ImageObject(GameManager manager) {
		super(manager);
	}

	@Override
	public void paint(Graphics graphics, GameManager manager, int x, int y, int width, int height) {
		if (animationManager == null) {
			graphics.setColor(Color.PINK);
			graphics.fillRect(x, y - height, width, height);
			return;
		}
		animationManager.paint(this, graphics, x, y, width, height);
	}

	@Override
	public void update(int time) {
		// IF YOU OVERRIDE THIS METHOD, DO NOT FORGET TO RUN THE SUPER VERSION AS IT IS
		// USED TO UPDATE THE ANIMATIONS
		animationManager.update(time);
		updateLocation(time);
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
	public void save(Element element, Document document) {
		super.save(element, document);

		animationManager.save(element, document);

	}

	@Override
	public String getReference() {
		return reference;
	}
}
