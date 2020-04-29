package main.java.com.booksaw.Engine2D.modifiers;

/**
 * This class is used to store all the required details about a modifier
 * 
 * @author booksaw
 *
 */
public class Modifier {

	public String reference, value, description;

	public Modifier(String reference, String value, String description) {
		this.reference = reference;
		this.value = value;
		this.description = description;
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
		return Integer.parseInt(value);
	}

}
