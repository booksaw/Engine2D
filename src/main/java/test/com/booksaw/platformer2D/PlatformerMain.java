package main.java.test.com.booksaw.platformer2D;

import main.java.com.booksaw.Engine2D.rendering.Engine2DFrame;

public class PlatformerMain {

	/**
	 * main method used to start the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Engine2DFrame.initFrame(700, 400);
		Engine2DFrame.setVisible(true);
		// setting the rendering manager
		PlatformGameManager manager = new PlatformGameManager();
		manager.setRendering(true);
		manager.resume();

	}

}
