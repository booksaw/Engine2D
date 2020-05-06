package main.java.com.booksaw.editor.window;

import java.awt.GridLayout;

import javax.swing.JPanel;

import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.panels.BlankPanel;
import main.java.com.booksaw.editor.panels.GamePanel;
import main.java.com.booksaw.editor.panels.Subdivision;
import main.java.com.booksaw.editor.panels.Topbar;
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
		Subdivision gamePanelDivision = new Subdivision(gamePanel, new BlankPanel(), false, true);

		Subdivision mainArea = new Subdivision(gamePanelDivision, new BlankPanel(), true, true);

		Subdivision topPanelSub = new Subdivision(new Topbar(), mainArea, false, false);
		topPanelSub.setPercentage(0.05);
		panel.add(topPanelSub.getPanel());
		panel.validate();
		panel.repaint();
		GamePanel.manager.pause(false);
		return panel;
	}

}
