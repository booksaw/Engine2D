package main.java.com.booksaw.editor.window;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.panels.GameObjectList;
import main.java.com.booksaw.editor.panels.GameObjectSelectorPanel;
import main.java.com.booksaw.editor.panels.GamePanel;
import main.java.com.booksaw.editor.panels.ObjectModifierPanel;
import main.java.com.booksaw.editor.panels.Subdivision;
import main.java.com.booksaw.editor.panels.TabbedPane;
import main.java.com.booksaw.editor.panels.Topbar;
import main.java.test.com.booksaw.platformer2D.PlatformGameManager;

/**
 * This is the main display frame, this is used to manage most the rendering for
 * the system
 * 
 * @author booksaw
 *
 */
public class MainPanel implements Window, ActionListener {

	@Override
	public JPanel getPanel(JFrame frame) {
		setupMenu(frame);

		JPanel panel = new JPanel(new GridLayout());

		panel.setBackground(Constants.mainBackground);
		// setting up the game panel
		GamePanel gamePanel = new GamePanel(new PlatformGameManager(), null);
		// setting up the tabbed pane
		TabbedPane tabbedPane = new TabbedPane(null);
		tabbedPane.addPanel(new GameObjectList(null), "Objects");
		tabbedPane.addPanel(new ObjectModifierPanel(null), "Modifiers");

		Subdivision gamePanelDivision = new Subdivision(gamePanel, new GameObjectSelectorPanel(null), false, true,
				null);

		Subdivision mainArea = new Subdivision(gamePanelDivision, tabbedPane, true, true, null);

		Subdivision topPanelSub = new Subdivision(new Topbar(null), mainArea, false, false, null);
		topPanelSub.setPercentage(0.05);
		panel.add(topPanelSub.getPanel());
		panel.validate();
		panel.repaint();
		GamePanel.manager.pause(false);
		return panel;
	}

	public void setupMenu(JFrame frame) {
		JMenuBar bar = new JMenuBar();
		// Build the first menu.
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		bar.add(menu);

		JMenuItem save = new JMenuItem("Save");
		save.setActionCommand("save");
		save.addActionListener(this);
		menu.add(save);

		JMenuItem saveas = new JMenuItem("Save as...");
		saveas.setActionCommand("saveas");
		saveas.addActionListener(this);
		menu.add(saveas);

		frame.setJMenuBar(bar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "save":
			GamePanel.manager.level.saveLevel();
			break;
		case "saveas":
			break;
		}
	}

}
