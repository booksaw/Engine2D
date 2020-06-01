package main.java.com.booksaw.editor.window;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Level;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.panels.GameObjectList;
import main.java.com.booksaw.editor.panels.GameObjectSelectorPanel;
import main.java.com.booksaw.editor.panels.GamePanel;
import main.java.com.booksaw.editor.panels.ObjectModifierPanel;
import main.java.com.booksaw.editor.panels.Subdivision;
import main.java.com.booksaw.editor.panels.TabbedPane;
import main.java.com.booksaw.editor.panels.Topbar;

/**
 * This is the main display frame, this is used to manage most the rendering for
 * the system
 * 
 * @author booksaw
 *
 */
public class MainPanel implements Window, ActionListener {

	private JFrame frame;
	private GameManager manager;

	public MainPanel(GameManager manager) {
		this.manager = manager;
	}

	@Override
	public JPanel getPanel(JFrame frame) {
		this.frame = frame;

		setupMenu(frame);
		JPanel panel = new JPanel(new GridLayout());

		panel.setBackground(Constants.mainBackground);
		// setting up the game panel
		GamePanel gamePanel = new GamePanel(manager, null);
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

		JMenuItem newLevel = new JMenuItem("New Level");
		newLevel.setActionCommand("new");
		newLevel.addActionListener(this);
		menu.add(newLevel);

		JMenuItem loadLevel = new JMenuItem("Load Level");
		loadLevel.setActionCommand("load");
		loadLevel.addActionListener(this);
		menu.add(loadLevel);

		frame.setJMenuBar(bar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "save":
			if (GamePanel.manager.level.hasFile()) {
				GamePanel.manager.level.saveLevel();
			} else {
				saveAs();
			}

			break;
		case "saveas":
			saveAs();
			break;
		case "new":
			newLevel();
			break;
		case "load":
			LaunchWindow.load(frame);
			break;
		}
	}

	/**
	 * This is used to save the file as, it has been separated into a separate
	 * method for improved code readability
	 */
	public void saveAs() {

		JFileChooser chooser;
		if (GamePanel.manager.level.hasFile()) {
			chooser = new JFileChooser(GamePanel.manager.level.getFile());
		} else {
			chooser = new JFileChooser(new File(System.getProperty("user.dir")));
		}
		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "XML files";
			}

			@Override
			public boolean accept(File f) {
				return f.getAbsolutePath().endsWith(".xml");
			}
		});
		int result = chooser.showSaveDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();

			if (!selectedFile.getAbsolutePath().endsWith(".xml")) {
				selectedFile = new File(selectedFile.getAbsolutePath() + ".xml");
			}

			if (!selectedFile.exists()) {
				try {
					selectedFile.createNewFile();

				} catch (Exception ex) {
					Logger.Log(LogType.ERROR, "Could not create the new file to store the level to " + selectedFile);
				}
			}

			GamePanel.manager.level.setLevelFile(selectedFile);
		}
	}

	/**
	 * This is used to save the file as, it has been separated into a separate
	 * method for improved code readability
	 */
	public void newLevel() {
		GamePanel.manager.setLevel(new Level(GamePanel.manager));
		GamePanel.validateAll();
	}

	@Override
	public Dimension getStartingSize() {
		return new Dimension(1280, 720);
	}

	@Override
	public boolean canResize() {
		return true;
	}

}
