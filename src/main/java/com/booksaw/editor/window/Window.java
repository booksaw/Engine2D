package main.java.com.booksaw.editor.window;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface Window {

	public JPanel getPanel(JFrame frame);

	public Dimension getStartingSize();
}
