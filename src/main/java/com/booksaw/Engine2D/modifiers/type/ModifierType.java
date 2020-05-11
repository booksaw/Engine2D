package main.java.com.booksaw.Engine2D.modifiers.type;

import javax.swing.JComponent;

import main.java.com.booksaw.Engine2D.modifiers.Modifier;

/**
 * This class is used to set the methods required within the enum
 * 
 * @author booksaw
 *
 */
public interface ModifierType {
	/**
	 * Used to get the JComponent for that type of input
	 * 
	 * @param parent   the parent (to add listeners for any sub components)
	 * @param modifier this is used to get the starting value
	 * @return the input component for this input type
	 */
	public JComponent getInput(Object parent, Modifier modifier);

	/**
	 * Used to update the value when it has been changed
	 * 
	 * @param modifier  the modifier to update
	 * @param component the component which was used to store the value
	 */
	public void handleInput(Modifier modifier, JComponent component);

}