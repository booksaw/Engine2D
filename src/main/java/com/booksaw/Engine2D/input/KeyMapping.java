package main.java.com.booksaw.Engine2D.input;

import java.util.List;

/**
 * This is used to add a key mapping within your game, this links each action
 * with a list of keys which trigger it
 * 
 * @author booksaw
 *
 */
public class KeyMapping {

	/**
	 * The reference for the trigger
	 */
	public String reference;

	/**
	 * A hashmap of keys which trigger the reference to be activated along with if
	 * they are pressed or not
	 */
	public List<Integer> keys;

	/**
	 * Used to track if this mapping is active
	 */
	private int pressed = 0;

	/**
	 * Used to create a new key mapping with its trigger
	 * 
	 * @param reference the reference which is updated when the trigger is activated
	 * @param keys      the list of key references which activate this trigger
	 */
	public KeyMapping(String reference, List<Integer> keys) {
		this.reference = reference;
		this.keys = keys;

	}

	/**
	 * Returns if any of the provided keys are pressed
	 * 
	 * @return if this trigger is pressed
	 */
	public boolean isPressed() {
		return pressed >= 1;
	}

	/**
	 * Used when a key is pressed
	 * 
	 * @param reference
	 */
	public void pressed(Integer reference) {
		// looping through all references
		for (Integer temp : keys) {
			// increasing the trigger count
			if (temp == reference) {
				pressed++;
				// returning as the same key should not be included twice
				return;
			}
		}

	}

	/**
	 * Used when a key is released
	 * 
	 * @param reference
	 */
	public void released(Integer reference) {
		// looping through all references
		for (Integer temp : keys) {
			if (reference == temp) {
				// reducing the trigger count
				if (pressed > 0) {
					pressed--;
					// returning as the same key should not be included twice
					return;
				}
			}
		}
	}

	/**
	 * Used to clear all inputs, useful for example when moving to a menu or when a
	 * key is meant to be tapped not held, using purge would require the key to be
	 * pressed again to get a response
	 */
	public void purge() {
		pressed = 0;
	}

}
