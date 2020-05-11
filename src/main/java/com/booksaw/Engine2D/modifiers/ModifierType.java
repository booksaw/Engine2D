package main.java.com.booksaw.Engine2D.modifiers;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public enum ModifierType {
	/**
	 * This is for string inputs
	 */
	STRING(new ModifierTypeInterface() {

		@Override
		public JComponent getInput(Object parent, Modifier modifier) {
			JTextArea area = new JTextArea(modifier.getStringValue());
			area.setBorder(new EmptyBorder(0, 3, 0, 3));
			return area;
		}

		@Override
		public void handleInput(Modifier modifier, JComponent component) {
			modifier.setValue(((JTextArea) component).getText());
		}

	});

	ModifierTypeInterface modifierInterface;

	private ModifierType(ModifierTypeInterface modifierInterface) {
		this.modifierInterface = modifierInterface;
	}

	/**
	 * This class is used to set the methods required within the enum
	 * 
	 * @author booksaw
	 *
	 */
	public interface ModifierTypeInterface {
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
}
