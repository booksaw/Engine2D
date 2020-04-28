package main.java.com.booksaw.Engine2D.objects;

/**
 * This is the class which tracks the location and graphics of the camera
 * 
 * @author booksaw
 *
 */
public class Camera {

	public int x, y, width, height, preferredWidth, preferredHeight, offsetX, offsetY;
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
		this.preferredWidth = width;
		this.preferredHeight = height;
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
		this.height = Math.round(width * (preferredHeight / preferredWidth));

		// checking the height is not too great
		if (this.height > height) {
			// changing the dimensions so height is the leading dimension
			this.height = height;
			// calculating the width using the correct apsect ratio
			this.width = Math.round(height * (preferredWidth / preferredWidth));

			// calculating the scale
			scale = width / preferredWidth;
			// calculating the offset
			offsetX = 0;
			offsetY = (height - this.height) / 2;
		} else {
			// calculating the scale
			scale = height / preferredHeight;
			// calculating the offset
			offsetY = 0;
			offsetX = (width - this.width) / 2;

		}

	}

}
