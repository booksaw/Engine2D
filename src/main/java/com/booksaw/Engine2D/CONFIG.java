package main.java.com.booksaw.Engine2D;

import java.io.File;

/**
 * This class is used to store default values which can be used across the
 * program, for example game name these values should be initialized when the
 * program is run, before it interacts with the game engine.
 * 
 * @author booksaw
 *
 */
public class CONFIG {
	/**
	 * This is the name of the project (used for the JFrame name and more)
	 */
	public static String NAME = "Example Name";

	/**
	 * This is the time of each game tick in milliseconds
	 */
	public static int tickLength = 10;

	/**
	 * A path to all assets of the program
	 */
	public static String assetPath = "platformer2D" + File.separator;
}
