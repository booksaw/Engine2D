package main.java.com.booksaw.Engine2D.rendering.animation;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * Used to create an invidiaual animation, a simple class to store data about
 * the animation
 * 
 * @author booksaw
 *
 */
public class Animation {

	/**
	 * Used to load an animation which is stored in file form
	 * 
	 * @param folder    the folder in which the animation is stored
	 * @param reference the reference name of the animation
	 * @return the animation once in object form, this will return null if there is
	 *         a problem with loading the animation
	 */
	public static Animation loadAnimation(File folder, String reference) {

		// checking the folder is a directory
		if (!folder.isDirectory()) {
			Logger.Log(LogType.ERROR,
					"Tried loading animation, but provided folder was not a directory: " + folder.getPath());
			return null;
		}

		File imageFile = new File(folder.getPath() + File.separator + reference + ".png");
		if (!imageFile.exists()) {
			Logger.Log(LogType.ERROR,
					"Tried loading animation, but provided file did not exist: " + imageFile.getPath());
			return null;
		}

		BufferedImage image = Utils.loadTransparentImage(imageFile);
		// loading the animation file
		File animationFile = new File(folder.getPath() + File.separator + reference + ".animation");
		// checking the provided animation has a file
		if (!animationFile.exists()) {
			// creating simple static animation
			return new Animation(image, reference);
		}

		// loading the animation file
		try {
			BufferedReader reader = new BufferedReader(new FileReader(animationFile));
			int frameCount = Integer.parseInt(reader.readLine());
			int timeGap = Integer.parseInt(reader.readLine());
			reader.close();
			return new Animation(image, reference, frameCount, timeGap);
		} catch (Exception e) {
			Logger.Log(LogType.ERROR,
					"Tried loading animation, but provided file could not load: " + animationFile.getPath());
			return new Animation(image, reference);
		}
	}

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
		this.timeGap = timeGap;
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

	/**
	 * Used to save the animation to a file - only save when changes are made
	 * 
	 * @param folder the folder to save the image to
	 */
	public void saveAnimation(File folder) {
		if (!folder.isDirectory()) {
			Logger.Log(LogType.ERROR,
					"Tried saving animation, but provided folder was not a directory: " + folder.getPath());
			return;
		}

		// saving the image
		File imageFile = new File(folder.getPath() + File.separator + reference + ".png");
		if (!imageFile.exists()) {
			// creating the file
			try {
				imageFile.createNewFile();
			} catch (Exception e) {
				Logger.Log(LogType.ERROR, "Could not create the image file: " + imageFile.getPath());
				return;
			}
		}
		// using png as it supports transparency
		try {
			ImageIO.write(image, "png", imageFile);
		} catch (IOException e) {
			Logger.Log(LogType.ERROR, "Could not save the image to file: " + imageFile.getPath());
			return;
		}

		// only saving pairing file if needed
		if (frameCount > 1 || timeGap > 0) {
			// saving the details
			File animationFile = new File(folder.getPath() + File.separator + reference + ".animation");
			if (!imageFile.exists()) {
				// creating the file
				try {
					imageFile.createNewFile();
				} catch (Exception e) {
					Logger.Log(LogType.ERROR, "Could not create the animation file: " + animationFile.getPath());
					return;
				}
			}

			try {
				PrintWriter writer = new PrintWriter(animationFile);
				writer.println(frameCount);
				writer.println(timeGap);

				writer.close();
			} catch (Exception e) {
				Logger.Log(LogType.ERROR, "Could not save the animation to file: " + animationFile.getPath());
			}

		}

	}

}
