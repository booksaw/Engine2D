package main.java.com.booksaw.editor.panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

import main.java.com.booksaw.Engine2D.logging.Logger;
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

		pane.setOpaque(true);
		panel.setOpaque(true);

		panel.setBackground(Color.orange);
		pane.setBackground(Constants.mainBackground);
		pane.setUI(new customTabbedPaneUI());
		pane.updateUI();
		Logger.Log(pane.getUI() + "");
		panel.setBorder(new LineBorder(Color.red, 5));
		panel.setForeground(Color.white);
//		pane.setForeground(Color.WHITE);
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

	public class customTabbedPaneUI extends MetalTabbedPaneUI {

	}

}
