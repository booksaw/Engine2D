package main.java.com.booksaw.Engine2D.collision;

import java.awt.Shape;
import java.awt.geom.Area;

/**
 * Used to carry out all processes relating to collisions
 * 
 * @author booksaw
 *
 */
public class CollisionManager {

	/**
	 * Used to check if an object is colliding with another object
	 * 
	 * @param hitbox the hitbox of the other object
	 * @return true if the objects are colliding, false if they are not
	 */
	public static boolean isColliding(Shape s1, Shape s2) {
		Area a1 = new Area(s1);
		Area a2 = new Area(s2);
		a1.intersect(a2);

		if (a1.isEmpty()) {
			return false;
		}
		return true;
	}

}
