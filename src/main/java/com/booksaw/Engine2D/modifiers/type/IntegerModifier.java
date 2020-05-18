package main.java.com.booksaw.Engine2D.modifiers.type;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import main.java.com.booksaw.Engine2D.modifiers.Modifier;

/**
 * This class is used for selecting integer modifiers in the editor
 * 
 * @author booksaw
 *
 */
public class IntegerModifier implements ModifierType {

	@Override
	public JComponent getInput(Object parent, Modifier modifier) {
		JTextArea area = new JTextArea(modifier.getStringValue());
		area.setBorder(new EmptyBorder(0, 3, 0, 3));
		return area;
	}

	@Override
	public void handleInput(Modifier modifier, JComponent component) {
		double input;
		try {
			input = Integer.parseInt(((JTextArea) component).getText());
		} catch (NumberFormatException e) {
			((JTextArea) component).setText(modifier.getIntValue() + "");
			return;
		}

		modifier.setValue(input);
	}

}
