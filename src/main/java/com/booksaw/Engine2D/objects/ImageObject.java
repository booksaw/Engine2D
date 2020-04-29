package main.java.com.booksaw.Engine2D.objects;

import java.awt.Graphics;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;

/**
 * Less primitive class, used to display objects which have a location somewhere
 * in the level
 * 
 * @author booksaw
 *
 */
public class ImageObject extends Object implements Updateable {

	@Override
	public void paint(Graphics graphics, GameManager manager, int x, int y, int width, int height) {
		// TODO use animation class to render
	}

	@Override
	public void update(int time) {
		// IF YOU OVERRIDE THIS METHOD, DO NOT FORGET TO RUN THE SUPER VERSION AS IT IS
		// USED TO UPDATE THE ANIMATIONS
		// TODO update animations
	}

}
