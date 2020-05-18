package main.java.com.booksaw.Engine2D.modifiers;

import java.awt.Color;

import javax.swing.JComponent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.modifiers.type.ModifierType;
import main.java.com.booksaw.Engine2D.modifiers.type.StringModifier;

/**
 * This class is used to store all the required details about a modifier. This
 * class has two main uses, firstly to load the default settings of the game
 * (for example the resolution) and secondly to manage variables stored within
 * gameObjects, this is useful so the editor can easily draw up
 * 
 * @author booksaw
 *
 */
public class Modifier {

	/**
	 * Used to store basic information about the modifier
	 * <p>
	 * The reference is the internal reference for the modifier
	 * </p>
	 * <p>
	 * The description is the explanation which can be used when displaying the
	 * modifier to the user
	 * </p>
	 */
	private String reference, description;

	/**
	 * This is used to store the value of the modifier
	 */
	Object value;

	/**
	 * Stores the modifier type (to allow for improved selection within the editor),
	 * this defaults to string modifier as it is the most basic
	 */
	private ModifierType type = new StringModifier();

	/**
	 * Used to create a modifier from the provided information
	 * 
	 * @param reference   the internal reference for the modifier
	 * @param value       the initial value that the modifier should take
	 * @param description a description of the modifier, displayed in the editor to
	 *                    make it clear to the user what it does
	 */
	public Modifier(String reference, Object value, String description) {
		this.reference = reference;
		this.value = value;
		this.description = description;
	}

	/**
	 * Used to load a modifier from the specified file
	 * 
	 * @param reference   the internal reference for the modifier (this is also the
	 *                    save reference)
	 * @param description the description of the modifier (this is not loaded but
	 *                    instead hard coded)
	 * @param element     the element where the modifier value is saved
	 */
	public Modifier(String reference, String description, Element element) {
		value = Utils.getTagString(reference, element);
		this.reference = reference;
		this.description = description;
	}

	/**
	 * Used to load a modifier from the specified file
	 * 
	 * @param referencethe   internal reference for the modifier (this is also the
	 *                       save reference)
	 * @param descriptionthe description of the modifier (this is not loaded but
	 *                       instead hard coded)
	 * @param element        the element where the modifier value is saved
	 * @param type           The type of the modifier, this is used within the
	 *                       editor when allowing the user to change the value
	 */
	public Modifier(String reference, String description, Element element, ModifierType type) {
		value = Utils.getTagString(reference, element);
		this.reference = reference;
		this.description = description;
		this.type = type;
	}

	/**
	 * Used to create a modifier from the provided information
	 * 
	 * @param reference   the internal reference for the modifier
	 * @param value       the initial value that the modifier should take
	 * @param description a description of the modifier, displayed in the editor to
	 *                    make it clear to the user what it does
	 * @param type        the type of the modifier, this is used within the editor
	 *                    when allowing the user to change the value
	 */
	public Modifier(String reference, Object value, String description, ModifierType type) {
		this(reference, value, description);
		this.type = type;
	}

	/**
	 * Used when saving a modifier
	 */
	@Override
	public String toString() {
		return reference + ":" + value + ":" + description;
	}

	/**
	 * An easy way of getting the value in integer form
	 * 
	 * @return the integer of the value
	 */
	public int getIntValue() {
		if (value instanceof Integer) {
			return ((Integer) value);
		}
		try {
			return Integer.parseInt(value.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * An easy way of getting the value in double form
	 * 
	 * @return the double of the value
	 */
	public double getDoubleValue() {
		if (value instanceof Double) {
			return ((Double) value);
		}
		try {
			return Double.parseDouble(value.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * An easy way of getting the value in boolean form
	 * 
	 * @return the boolean of the value
	 */
	public boolean getBooleanValue() {
		if (value instanceof Boolean) {
			return ((Boolean) value);
		}

		try {
			return Boolean.parseBoolean(value.toString());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * An easy way of getting the value in string form
	 * 
	 * @return the string of the value
	 */
	public String getStringValue() {
		return value.toString();
	}

	public Color getColorValue() {
		if (value instanceof Color) {
			return ((Color) value);
		}

		try {
			return new Color(getIntValue());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @return the internal reference for this modifier
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the new internal reference for this modifier
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return The object value of this modifier
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the new value of the modifier
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the description of the modifier
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the new description of the modifier
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Used to save the modifier value to file
	 * 
	 * @param element  the xml element to save it to
	 * @param document the xml document
	 */
	public void Save(Element element, Document document) {
		Utils.saveValue(reference, document, element, value.toString());
	}

	/**
	 * Used to get the input component for this modifier
	 * 
	 * @param parent the parent (for any listeners)
	 * @return the input component
	 */
	public JComponent getComponent(Object parent) {
		JComponent component = type.getInput(parent, this);
		component.setName(reference);
		return component;
	}

	/**
	 * This is used to handle any updates to save the changed values
	 * 
	 * @param component
	 */
	public void handleInput(JComponent component) {
		type.handleInput(this, component);
	}

}
