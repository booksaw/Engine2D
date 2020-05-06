package main.java.com.booksaw.editor.window;

import java.awt.GridLayout;

import javax.swing.JPanel;

import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.panels.BlankPanel;
import main.java.com.booksaw.editor.panels.Subdivision;

/**
 * This is the main display frame, this is used to manage most the rendering for
 * the system
 * 
 * @author booksaw
 *
 */
public class MainPanel implements Window {

	@Override
	public JPanel getPanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBackground(Constants.mainBackground);
		panel.add(new Subdivision(new Subdivision(new BlankPanel(), new BlankPanel(), true), new BlankPanel(), false)
				.getPanel());

		return panel;
	}

}
