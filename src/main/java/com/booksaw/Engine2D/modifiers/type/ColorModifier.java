package main.java.com.booksaw.Engine2D.modifiers.type;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

import main.java.com.booksaw.Engine2D.modifiers.Modifier;

/**
 * This class is used to give users a way to select a color as a modifier
 * 
 * @author booksaw
 *
 */
public class ColorModifier implements ModifierType, ActionListener {

	private transient Modifier modifier;

	@Override
	public JComponent getInput(Object parent, Modifier modifier) {
		this.modifier = modifier;
		JButton colorPicker = new JButton("Choose Color");
		colorPicker.addActionListener(this);
		return colorPicker;
	}

	@Override
	public void handleInput(Modifier modifier, JComponent component) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Color c = JColorChooser.showDialog(null, "Select Color", new Color(modifier.getIntValue()));
		if (c != null) {
			modifier.setValue(c.getRGB());
		}

	}

}
