package main.java.com.booksaw.Engine2D.modifiers.type;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import main.java.com.booksaw.Engine2D.modifiers.Modifier;
import main.java.com.booksaw.editor.Constants;

public class BooleanModifier implements ModifierType {

	@Override
	public JComponent getInput(Object parent, Modifier modifier) {
		JCheckBox box = new JCheckBox();
		box.setSelected(modifier.getBooleanValue());
		box.setBackground(Constants.componentBackground);
		return box;
	}

	@Override
	public void handleInput(Modifier modifier, JComponent component) {
		modifier.setValue(((JCheckBox) component).isSelected());
	}

}
