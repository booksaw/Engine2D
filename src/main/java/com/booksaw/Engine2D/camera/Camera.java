package main.java.com.booksaw.Engine2D.camera;

import java.awt.Rectangle;

/**
 * This is the class which tracks the location and graphics of the camera
 * 
 * @author booksaw
 *
 */
public class Camera {

	public double x, y, width, height, preferredWidth, preferredHeight;
	public int offsetX, offsetY;
	public double scale;

	/**
	 * This constructor takes basic location information to use during rendering.
	 * 
	 * @param x               the x location of the camera
	 * @param y               the y location of the camera
	 * @param preferredWidth  the preferred width of the camera (before scaling)
	 * @param preferredHeight the preferred height of the camera (before scaling)
	 * @param width           the starting width of the JFrame
	 * @param height          the starting height of the JFrame
	 * 
	 */
	public Camera(int x, int y, int preferredWidth, int preferredHeight, int width, int height) {
		this.x = x;
		this.y = y;
		this.preferredWidth = preferredWidth;
		this.preferredHeight = preferredHeight;
		resize(width, height);

	}

	/**
	 * Used whenever the JFrame is resized. this should be run to correct camera
	 * settings
	 * 
	 * @param width  the new width of the JFrame
	 * @param height the new height of the JFrame
	 */
	public void resize(int width, int height) {
		this.width = width;
		// calculating the height using the aspect ratio
		this.height = (int) (width * ((double) preferredHeight / preferredWidth));

		// checking the height is not too great
		if (this.height > height) {
			// changing the dimensions so height is the leading dimension
			this.height = height;
			// calculating the width using the correct apsect ratio
			this.width = (int) (height * ((double) preferredWidth / preferredHeight));

			// calculating the scale
			scale = (double) height / preferredHeight;
			// calculating the offset
			offsetY = 0;
			offsetX = (int) ((width - this.width) / 2);
		} else {
			// calculating the scale
			scale = (double) width / preferredWidth;
			// calculating the offset
			offsetX = 0;
			offsetY = (int) ((height - this.height) / 2);

		}

	}

	/**
	 * This is used to get the Rectangle associated with the camera, useful for checking if an object is on the screen
	 * @return the hitbox of the camera
	 */
	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, (int) preferredWidth, (int) preferredWidth);
	}
}
