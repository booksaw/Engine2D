package main.java.com.booksaw.Engine2D.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

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

	protected static String reference = "imageObject";

	public static String getReference() {
		return reference;
	}

	protected AnimationManager animationManager;

	public ImageObject(GameManager gameManager, Element details) {
		super(gameManager, details);

	}

	public ImageObject(AnimationManager manager, GameManager gameManager) {
		super(gameManager);
		this.animationManager = manager;
		gameManager.addUpdatable(this);
	}

	@Override
	public void paint(Graphics graphics, GameManager manager, int x, int y, int width, int height) {
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

}
