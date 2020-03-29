package main.java.com.booksaw.Engine2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * A class containing useful code snippets to avoid them being repeated across
 * the program (USE THESE INSTEAD OF MAKING CUSTOM VERSIONS, this means that if
 * any changes need to be made to these methods, other methods do not need
 * changing)
 * 
 * @author booksaw
 *
 */
public class Utils {

	/**
	 * Used to load an image file
	 * 
	 * @param file the file to load
	 * @return a buffered image version of the image
	 */
	public BufferedImage loadImage(File file) {

		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			// if there is an error while loading
			Logger.Log(LogType.ERROR, "Could not load image file (" + file.getPath() + ")");
			e.printStackTrace();
			return null;
		}

	}

}
