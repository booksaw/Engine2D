package main.java.com.booksaw.editor.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.SelectionManager;
import main.java.com.booksaw.editor.mouse.MouseFunction;

public class Topbar extends Panel implements ActionListener {

	public Topbar(Panel parent) {
		super(parent);
	}

	JButton play, pause, stop, grid;
	ImageIcon gridEnabled, gridDisabled;

	@Override
	protected void createPanel(JPanel panel) {
		panel.setLayout(new FlowLayout(0, 5, 5));
		panel.setBackground(Constants.mainBackground);

		play = new JButton(
				new ImageIcon(Utils.loadTransparentImage(new File("Engine2D" + File.separator + "play.png"))));
		play.setSelectedIcon(
				new ImageIcon(Utils.loadTransparentImage(new File("Engine2D" + File.separator + "play-disabled.png"))));
		play.setMaximumSize(new Dimension(25, 20));
		play.setBorder(new EmptyBorder(2, 5, 2, 2));
		play.setBackground(Constants.mainBackground);
		play.setContentAreaFilled(false);
		play.setFocusable(false);
		play.setBorderPainted(false);
		play.setForeground(Constants.mainBackground);
		play.setActionCommand("play");
		play.addActionListener(this);

		pause = new JButton(
				new ImageIcon(Utils.loadTransparentImage(new File("Engine2D" + File.separator + "pause.png"))));
		pause.setSelectedIcon(new ImageIcon(
				Utils.loadTransparentImage(new File("Engine2D" + File.separator + "pause-disabled.png"))));
		pause.setMaximumSize(new Dimension(25, 20));
		pause.setBorder(new EmptyBorder(2, 5, 2, 2));
		pause.setBackground(Constants.mainBackground);
		pause.setContentAreaFilled(false);
		pause.setFocusable(false);
		pause.setBorderPainted(false);
		pause.setForeground(Constants.mainBackground);
		pause.setActionCommand("pause");
		pause.addActionListener(this);
		pause.setEnabled(false);

		stop = new JButton(
				new ImageIcon(Utils.loadTransparentImage(new File("Engine2D" + File.separator + "stop.png"))));
		stop.setSelectedIcon(
				new ImageIcon(Utils.loadTransparentImage(new File("Engine2D" + File.separator + "stop-disabled.png"))));
		stop.setMaximumSize(new Dimension(25, 20));
		stop.setBorder(new EmptyBorder(2, 5, 2, 2));
		stop.setBackground(Constants.mainBackground);
		stop.setContentAreaFilled(false);
		stop.setFocusable(false);
		stop.setBorderPainted(false);
		stop.setForeground(Constants.mainBackground);
		stop.setActionCommand("stop");
		stop.addActionListener(this);

		gridEnabled = new ImageIcon(Utils.loadTransparentImage(new File("Engine2D" + File.separator + "grid.png")));
		gridDisabled = new ImageIcon(
				Utils.loadTransparentImage(new File("Engine2D" + File.separator + "grid-disabled.png")));

		grid = new JButton(gridEnabled);
		grid.setMaximumSize(new Dimension(25, 20));
		grid.setBorder(new EmptyBorder(2, 5, 2, 2));
		grid.setBackground(Constants.mainBackground);
		grid.setContentAreaFilled(false);
		grid.setFocusable(false);
		grid.setBorderPainted(false);
		grid.setForeground(Constants.mainBackground);
		grid.setActionCommand("grid");
		grid.setIcon((GamePanel.grid) ? gridEnabled : gridDisabled);
		grid.addActionListener(this);
		grid.setToolTipText("Lock to grid");

		panel.add(play);
		panel.add(pause);
		panel.add(stop);
		panel.add(grid);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (MouseFunction.activeFunction != MouseFunction.GENERAL) {
			return;
		}

		switch (e.getActionCommand()) {
		case "play":
			if (play.isEnabled()) {
				play.setEnabled(false);
				pause.setEnabled(true);
				SelectionManager.clearSelection();
				GamePanel.manager.resume();
			}
			break;
		case "pause":
			if (pause.isEnabled()) {
				play.setEnabled(true);
				pause.setEnabled(false);
				GamePanel.manager.pause();
			}
			break;
		case "stop":
			if (stop.isEnabled()) {
				pause.setEnabled(false);
				play.setEnabled(true);

				GamePanel.manager.level.reset();
				GamePanel.manager.pause();
				GamePanel.manager.level.reset();
			}
			break;
		case "grid":
			GamePanel.grid = (GamePanel.grid) ? false : true;
			grid.setIcon((GamePanel.grid) ? gridEnabled : gridDisabled);
			break;
		default:
			Logger.Log(LogType.ERROR, "An action command was not understood: " + e.getActionCommand());
		}
	}

}
