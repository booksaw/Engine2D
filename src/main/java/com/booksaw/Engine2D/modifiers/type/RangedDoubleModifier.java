package main.java.com.booksaw.Engine2D.modifiers.type;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import main.java.com.booksaw.Engine2D.modifiers.Modifier;

/**
 * This class is used to take an input for a double which needs to be in a
 * specific range
 * 
 * @author booksaw
 *
 */
public class RangedDoubleModifier implements ModifierType {

	double min, max;

	public RangedDoubleModifier(double min, double max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public JComponent getInput(Object parent, Modifier modifier) {
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(modifier.getDoubleValue(), min, max, 0.1));

		spinner.addChangeListener((ChangeListener) parent);

		return spinner;
	}

	@Override
	public void handleInput(Modifier modifier, JComponent component) {
		modifier.setValue(((JSpinner) component).getValue());
	}

}
