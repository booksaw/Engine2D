package main.java.com.booksaw.editor.window;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.Editor2DFrame;

public class LaunchWindow implements Window, ActionListener {

	JFrame frame;

	@Override
	public JPanel getPanel(JFrame frame) {
		this.frame = frame;
		JPanel panel = new JPanel();
		panel.setBackground(Constants.mainBackground);

		JButton loadLevel = new JButton("Load project");
		loadLevel.addActionListener(this);
		loadLevel.setActionCommand("load");
		panel.add(loadLevel);

		JButton newLevel = new JButton("New project");
		newLevel.addActionListener(this);
		newLevel.setActionCommand("new");
		panel.add(newLevel);

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
			load();
			return;
		}
	}

	/**
	 * This is used to save the file as, it has been separated into a separate
	 * method for improved code readability
	 */
	public void load() {

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

}
