package main.java.com.booksaw.Engine2D.modifiers;

/**
 * This class is used to store all the requried details about a modifier
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

}
