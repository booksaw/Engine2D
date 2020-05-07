package main.java.com.booksaw.editor.panels;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.LineBorder;

import main.java.com.booksaw.editor.Constants;

/**
 * Used to control all panels and sub panels within the editor
 * 
 * @author booksaw
 *
 */
public abstract class Panel {

	JPanel panel;
	boolean border = true;
	protected Panel parent;

	public Panel(Panel parent) {
		this.parent = parent;
	}

	public JPanel getPanel() {
		if (panel != null) {
			return panel;
		}
		panel = new JPanel();
		createPanel(panel);
		if (border)
			panel.setBorder(new LineBorder(Constants.border, 2));
		addListeners(panel);
		return panel;

	}

	protected abstract void createPanel(JPanel panel);

	protected void addListeners(JComponent component) {

		Panel active = this;
		if (component == null) {
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		do {
			if (component instanceof JTree) {
			}
			if (active instanceof MouseListener) {
				component.addMouseListener((MouseListener) active);
			} else if (active instanceof MouseMotionListener) {
				component.addMouseMotionListener((MouseMotionListener) active);
			}
		} while ((active = active.parent) != null);

	}

	public Panel getParent() {
		return parent;
	}

	public void setParent(Panel parent) {
		this.parent = parent;
		if (panel != null)
			addListeners(panel);
	}

}
