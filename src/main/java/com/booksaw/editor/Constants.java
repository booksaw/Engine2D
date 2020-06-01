package main.java.com.booksaw.editor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import main.java.com.booksaw.Engine2D.Utils;

/**
 * This class is used to store any constants for the editor panel so the look
 * and feel can be adjusted easily
 * 
 * @author nfgg2
 *
 */
public class Constants {

	static {
		fullIcon = Utils.loadTransparentImage(new File("Engine2D" + File.separator + "logo-full.png"));
		smallIcon = Utils.loadTransparentImage(new File("Engine2D" + File.separator + "logo-small.png"));
	}

	public static final Color mainBackground = new Color(48, 48, 48);
	public static final Color componentBackground = new Color(30, 30, 30);
	public static final Color border = new Color(70, 70, 70);

	public static BufferedImage fullIcon, smallIcon;

}
