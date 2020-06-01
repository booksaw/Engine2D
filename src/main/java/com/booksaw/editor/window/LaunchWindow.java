package main.java.com.booksaw.editor.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.Editor2DFrame;

public class LaunchWindow implements Window, ActionListener {

	JFrame frame;

	@Override
	public JPanel getPanel(JFrame frame) {
		this.frame = frame;
		JPanel panel = new JPanel(null);
		panel.setBackground(Constants.mainBackground);

		JLabel icon = new JLabel(new ImageIcon(Constants.fullIcon));
		icon.setBounds(350, 50, 300, 300);
		panel.add(icon);

		JButton loadLevel = new JButton("Load project");
		loadLevel.addActionListener(this);
		loadLevel.setActionCommand("load");
		loadLevel.setBounds(10, 100, 165, 25);

		loadLevel.setBackground(Color.white);
		panel.add(loadLevel);

		JButton newLevel = new JButton("New project");
		newLevel.addActionListener(this);
		newLevel.setActionCommand("new");
		newLevel.setBounds(10, 150, 165, 25);
		panel.add(newLevel);

		JButton wiki = new JButton("Wiki");
		wiki.addActionListener(this);
		wiki.setActionCommand("wiki");
		wiki.setBounds(10, 200, 165, 25);
		panel.add(wiki);

		JButton exit = new JButton("Quit");
		exit.addActionListener(this);
		exit.setActionCommand("quit");
		exit.setBounds(10, 250, 165, 25);
		panel.add(exit);

		return panel;
	}

	@Override
	public Dimension getStartingSize() {
		return new Dimension(700, 400);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "new":
			Editor2DFrame.setWindow(new MainPanel(new GameManager(null)));
			break;
		case "load":
			load(frame);
			return;
		case "wiki":
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					URL url = new URL("https://github.com/booksaw/Engine2D/wiki");
					desktop.browse(url.toURI());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			break;
		case "quit":
			Logger.Log(LogType.INFO, "System closing...");
			System.exit(0);
			break;
		}
	}

	/**
	 * This is used to save the file as, it has been separated into a separate
	 * method for improved code readability
	 * 
	 * @param frame the parent component for the file chooser
	 */
	public static void load(Component frame) {

		JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
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

		int result = chooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			Editor2DFrame.setWindow(new MainPanel(new GameManager(selectedFile)));
		}
	}

	@Override
	public boolean canResize() {
		return true;
	}

}
