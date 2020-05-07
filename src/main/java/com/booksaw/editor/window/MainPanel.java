package main.java.com.booksaw.editor.window;

import java.awt.GridLayout;

import javax.swing.JPanel;

import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.panels.BlankPanel;
import main.java.com.booksaw.editor.panels.GameObjectList;
import main.java.com.booksaw.editor.panels.GamePanel;
import main.java.com.booksaw.editor.panels.TabbedPane;
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
		// setting up the game panel
		GamePanel gamePanel = new GamePanel(new PlatformGameManager(), null);
		// setting up the tabbed pane
		TabbedPane tabbedPane = new TabbedPane(null);
		tabbedPane.addPanel(new GameObjectList(null), "Objects");

		Subdivision gamePanelDivision = new Subdivision(gamePanel, new BlankPanel(null), false, true, null);

		Subdivision mainArea = new Subdivision(gamePanelDivision, tabbedPane, true, true, null);

		Subdivision topPanelSub = new Subdivision(new Topbar(null), mainArea, false, false, null);
		topPanelSub.setPercentage(0.05);
		panel.add(topPanelSub.getPanel());
		panel.validate();
		panel.repaint();
		GamePanel.manager.pause(false);
		return panel;
	}

}
