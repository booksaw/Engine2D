package main.java.com.booksaw.editor.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import main.java.com.booksaw.editor.Constants;

/**
 * This class is used when multiple tabs are needed in the same region
 * 
 * @author booksaw
 *
 */
public class TabbedPane extends Panel {

	public TabbedPane(Panel parent) {
		super(parent);
	}

	JTabbedPane pane;
	List<Panel> panels = new ArrayList<>();

	@Override
	protected void createPanel(JPanel panel) {
		panel.setLayout(new GridLayout());

		UIManager.put("TabbedPane.foreground", Color.white);
		UIManager.put("TabbedPane.selected", Color.black);
		UIManager.put("TabbedPane.darkShadow", Constants.mainBackground);
		UIManager.put("TabbedPane.highlight", Constants.componentBackground);
		UIManager.put("TabbedPane.shadow", Constants.componentBackground);

		pane = new JTabbedPane();
		pane.setUI(new customTabbedPaneUI());
		pane.setOpaque(true);
		panel.setOpaque(true);

		panel.setBackground(Color.orange);
		panel.setFocusable(false);
		pane.setBackground(Constants.mainBackground);
		pane.setUI(new customTabbedPaneUI());
		panel.setBorder(new LineBorder(Color.red, 5));
		panel.setForeground(Color.white);
		addListeners(pane);

		panel.add(pane);
	}

	/**
	 * Used to add a panel to the window
	 * 
	 * @param panel the panel to add
	 */
	public void addPanel(Panel panel) {
		panel.setParent(this);
		panels.add(panel);
		// checking if this frame has been init
		if (pane == null) {
			getPanel();
		}
		pane.addTab(panel.getPanel().getName(), panel.getPanel());
	}

	/**
	 * Used to add a tab with the specified name
	 * 
	 * @param panel the panel to add
	 * @param name  the name of the tab
	 */
	public void addPanel(Panel panel, String name) {
		panel.setParent(this);
		panel.getPanel().setName(name);
		addPanel(panel);
	}

	@Override
	public void setParent(Panel parent) {
		super.setParent(parent);
		for (Panel p : panels) {
			p.setParent(this);
		}

		if (pane != null) {
			addListeners(pane);
		}
	}

	/**
	 * This is used to disable some unwanted UI elements
	 * 
	 * @author booksaw
	 *
	 */
	public class customTabbedPaneUI extends BasicTabbedPaneUI {

		/**
		 * Used to disable the gray border around the main panel
		 */
		@Override
		protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
		}

		/**
		 * Used to disable the blue focus indicator
		 */
		@Override
		protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
				Rectangle iconRect, Rectangle textRect, boolean isSelected) {
		}
	}

}
