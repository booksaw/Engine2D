package main.java;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Main {
	public static void main(String[] argv) throws Exception {
		JFrame frame = new JFrame("Tabbed Pane Sample");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("java2s.com", new JButton("a"));
		tabbedPane.addTab("java2s.com", new JButton("a"));

		tabbedPane.setForeground(Color.YELLOW);
		tabbedPane.setBackground(Color.MAGENTA);
		String label = "Tab Label";
		tabbedPane.addTab(label, new JButton("Button"));

		int index = tabbedPane.getTabCount() - 1;

//		tabbedPane.setForegroundAt(index, Color.ORANGE);
//		tabbedPane.setBackgroundAt(index, Color.GREEN);

		frame.add(tabbedPane, BorderLayout.CENTER);
		frame.setSize(400, 150);
		frame.setVisible(true);
	}
}