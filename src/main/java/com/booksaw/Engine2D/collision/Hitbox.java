package main.java.com.booksaw.Engine2D.collision;

import java.awt.Shape;

import main.java.com.booksaw.Engine2D.Vector;

/**
 * This class is the super class to control all types of collision
 * 
 * @author booksaw
 *
 */
public interface Hitbox {

	/**
	 * Returns the shape of this object
	 * 
	 * @return the shape of the object
	 */
	public Shape getShape();

	/**
	 * Returns the shape of this object after it has moved by a certain amount of
	 * units useful to test moving the object before actually moving it
	 * 
	 * @return the shape of the object
	 */
	public Shape getShape(Vector translation);

}
