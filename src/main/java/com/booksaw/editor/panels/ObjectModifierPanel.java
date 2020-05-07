package main.java.com.booksaw.editor.panels;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.java.com.booksaw.editor.Constants;

public class ObjectModifierPanel extends Panel {

	public ObjectModifierPanel(Panel parent) {
		super(parent);
	}

	@Override
	protected void createPanel(JPanel panel) {
		// TODO
		border = false;
		panel.setBorder(new LineBorder(Color.orange, 5));
		panel.setBackground(Constants.componentBackground);
	}

}
