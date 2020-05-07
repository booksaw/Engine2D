package main.java.com.booksaw.editor.panels;

import javax.swing.JPanel;
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

	public JPanel getPanel() {

		if (panel != null) {
			return panel;
		}
		panel = new JPanel();
		createPanel(panel);
		panel.setBorder(new LineBorder(Constants.border, 2));
		return panel;

	}

	protected abstract void createPanel(JPanel panel);

}
