package main.java.com.booksaw.Engine2D.objects;

/**
 * This is the class which tracks the location and graphics of the camera
 * 
 * @author booksaw
 *
 */
public class Camera {

	public int x, y, width, height, scale;

	/**
	 * This constructor takes basic location information to use during rendering.
	 * 
	 * @param x      the x location of the camera
	 * @param y      the y location of the camera
	 * @param width  the width of the default camera (in game units) so it can be
	 *               scaled accordingly
	 * @param height the height of the default camera (in game units) so it can be
	 *               scaled accordingly
	 */
	public Camera(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		scale = 1;
	}

}
