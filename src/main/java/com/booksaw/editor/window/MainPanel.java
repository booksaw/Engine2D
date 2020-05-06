package main.java.com.booksaw.editor.window;

import java.awt.GridLayout;

import javax.swing.JPanel;

import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.panels.BlankPanel;
import main.java.com.booksaw.editor.panels.GamePanel;
import main.java.com.booksaw.editor.panels.Subdivision;
import main.java.test.com.booksaw.platformer2D.PlatformGameManager;

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
		GamePanel gamePanel = new GamePanel(new PlatformGameManager());
		panel.add(new Subdivision(gamePanel, new BlankPanel(), false).getPanel());
		panel.validate();
		return panel;
	}

}
