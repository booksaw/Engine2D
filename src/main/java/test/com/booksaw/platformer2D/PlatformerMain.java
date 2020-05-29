package main.java.test.com.booksaw.platformer2D;

import java.io.File;

import javax.swing.JOptionPane;

import main.java.com.booksaw.Engine2D.rendering.Engine2DFrame;
import main.java.com.booksaw.editor.Editor2DFrame;
import main.java.com.booksaw.editor.window.LaunchWindow;

public class PlatformerMain {

	/**
	 * main method used to start the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// simple selection for testing purposes
		int result = JOptionPane.showConfirmDialog(null, "Show editor");
		if (result == JOptionPane.YES_OPTION) {
			Editor2DFrame.initFrame();
			Editor2DFrame.setWindow(new LaunchWindow());
			Editor2DFrame.setVisible(true);
			return;
		}
		Engine2DFrame.initFrame(700, 400);
		Engine2DFrame.setVisible(true);
		// setting the rendering manager
		PlatformGameManager manager = new PlatformGameManager(new File("level1.xml"));
		manager.setRendering(true);
		manager.resume();

	}

}
