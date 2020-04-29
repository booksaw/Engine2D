package main.java.com.booksaw.Engine2D.objects;

import java.awt.Graphics;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.Engine2D.rendering.animation.AnimationManager;

/**
 * Less primitive class, used to display objects which have a location somewhere
 * in the level
 * 
 * @author booksaw
 *
 */
public class ImageObject extends Object implements Updateable {

	protected AnimationManager animationManager;

	public ImageObject(AnimationManager manager) {
		this.animationManager = manager;
	}

	@Override
	public void paint(Graphics graphics, GameManager manager, int x, int y, int width, int height) {
		animationManager.paint(graphics, x, y, width, height);
	}

	@Override
	public void update(int time) {
		// IF YOU OVERRIDE THIS METHOD, DO NOT FORGET TO RUN THE SUPER VERSION AS IT IS
		// USED TO UPDATE THE ANIMATIONS
		animationManager.update(time);
	}

}
