package main.java.com.booksaw.editor.panels;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.SelectionManager;

public class ObjectModifierPanel extends Panel {

	public static ObjectModifierPanel modifierPanel;

	public ObjectModifierPanel(Panel parent) {
		super(parent);
		modifierPanel = this;
	}

	@Override
	protected void createPanel(JPanel panel) {
		border = false;

		panel.setBackground(Constants.componentBackground);
		update();
	}

	public void update() {
		panel.removeAll();
		if (SelectionManager.getSelection() == null) {
			JLabel label = new JLabel("Select an option");
			label.setForeground(Color.white);
			panel.add(label);
			panel.setForeground(Color.white);
		}
		
		
	}

}
