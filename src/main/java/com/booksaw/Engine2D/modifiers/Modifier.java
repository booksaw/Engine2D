package main.java.com.booksaw.Engine2D.modifiers;

/**
 * This class is used to store all the required details about a modifier
 * 
 * @author booksaw
 *
 */
public class Modifier {

	private String reference, description;
	Object value;

	public Modifier(String reference, Object value, String description) {
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

}
