package main.java.com.booksaw.editor;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;

import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.editor.mouse.MouseListener;
import main.java.com.booksaw.editor.window.Window;

/**
 * This is used to handle controlling the JFrame which is displaying the editor
 * 
 * @author nfgg2
 *
 */
public class Editor2DFrame implements ComponentListener, WindowListener {

	private static JFrame editorFrame;

	public static void initFrame() {
		Logger.Log(LogType.INFO, "Loading editor frame");
		// ui manager
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			Logger.Log(LogType.WARNING, "Could not load system UI, using Java inbuilt");
//		}
		editorFrame = new JFrame();
		editorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting the name of the frame to the determined one
		editorFrame.setTitle("Engine2D");
		editorFrame.setSize(1280, 720);
		editorFrame.setLocationRelativeTo(null);
		editorFrame.addKeyListener(new KeyboardManager());
		editorFrame.setMinimumSize(new Dimension(300, 300));
		KeyboardManager.keyboardManager.load(new File("platformer2D" + File.separator + "keymappings"));

		// adding listeners
		MouseListener listener = new MouseListener();
		editorFrame.addMouseListener(listener);
		editorFrame.addMouseMotionListener(listener);

		// used to detect when the frame is resized
		editorFrame.addComponentListener(new Editor2DFrame());
		editorFrame.addWindowListener(new Editor2DFrame());
	}

	/**
	 * Used to display the frame (used after all configuration has occurred)
	 * 
	 * @param show if the window should be displayed or hidden
	 */
	public static void setVisible(boolean show) {
		Logger.Log(editorFrame + "");
		// if initFrame() has not been run
		if (editorFrame == null) {
			Logger.Log(LogType.ERROR, "Editor frame cannot be displayed before it has been initilized");
			return;
		}
		// displaying the frame
		editorFrame.setVisible(show);
	}

	public static void setWindow(Window window) {
		editorFrame.setContentPane(window.getPanel());
		editorFrame.validate();
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

}
