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

	private String reference, description;
	Object value;
	private ModifierType type = new StringModifier();

	public Modifier(String reference, Object value, String description) {
		this.reference = reference;
		this.value = value;
		this.description = description;
	}

	public Modifier(String reference, String description, Element element) {
		value = Utils.getTagString(reference, element);
		this.reference = reference;
		this.description = description;
	}

	public Modifier(String reference, String description, Element element, ModifierType type) {
		value = Utils.getTagString(reference, element);
		this.reference = reference;
		this.description = description;
		this.type = type;
	}

	// used when saving the modifiers
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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
