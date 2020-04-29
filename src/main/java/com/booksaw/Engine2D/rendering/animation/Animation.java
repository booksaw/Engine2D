package main.java.com.booksaw.Engine2D.rendering.animation;

import java.awt.image.BufferedImage;

/**
 * Used to create an invidiaual animation, a simple class to store data about
 * the animation
 * 
 * @author booksaw
 *
 */
public class Animation {

	/**
	 * The image of all the animation frames, the frames should be vertically
	 * stacked
	 */
	public BufferedImage image;
	/**
	 * The reference to make the animation unique
	 */
	public String reference;
	/**
	 * Total number of frames within the animation (used to calculate the size of
	 * each frame)
	 */
	public int frameCount;

	/**
	 * Stores the height of each unique frame to save it being recalculated each
	 * time it is rendered.
	 */
	public int frameHeight;

	/**
	 * For when the animation auto updates, how many ticks before moving onto the
	 * next frame
	 */
	public int timeGap;

	/**
	 * Used to create an animation
	 * 
	 * @param image      the image of the animation
	 * @param reference  the reference which makes the animation unique
	 * @param frameCount the number of frames within the animation
	 * @param timeGap    the number of ticks before the animation advances to the
	 *                   next screen
	 */
	public Animation(BufferedImage image, String reference, int frameCount, int timeGap) {
		this.image = image;
		this.reference = reference;
		this.frameCount = frameCount;
		this.frameHeight = image.getHeight() / frameCount;
	}

	/**
	 * Used when a static image is going to be used
	 * 
	 * @param image     the static image to be used
	 * @param reference the reference for that image
	 */
	public Animation(BufferedImage image, String reference) {
		this(image, reference, 1, -1);
	}

}
