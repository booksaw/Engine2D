package main.java.com.booksaw.Engine2D.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * this is used to track which keys are pressed at any given point
 * 
 * @author booksaw
 *
 */
public class KeyboardManager implements KeyListener {

	public static KeyboardManager keyboardManager;

	/**
	 * Storing the static variable as the active keyboard manager
	 */
	public KeyboardManager() {
		keyboardManager = this;
		keyMappings = new HashMap<>();
	}

	/**
	 * Used to store all active key mappings
	 */
	private HashMap<String, KeyMapping> keyMappings;

	/**
	 * Used to add a new key mapping
	 * 
	 * @param mapping the key mapping to add
	 */
	public void addKeyMapping(KeyMapping mapping) {
		keyMappings.put(mapping.reference, mapping);
	}

	/**
	 * Used to remove a key mapping
	 * 
	 * @param reference the reference for the key mapping
	 */
	public void removeKeyMapping(String reference) {
		keyMappings.remove(reference);
	}

	/**
	 * Used to remove to clear all loaded key mappings
	 */
	public void clearKeyMappings() {
		keyMappings = new HashMap<>();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// unneeded, as pressed and released cover all needs required
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// updating the key mappings
		for (Entry<String, KeyMapping> tempString : keyMappings.entrySet()) {
			tempString.getValue().pressed(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// updating the key mappings
		for (Entry<String, KeyMapping> tempString : keyMappings.entrySet()) {
			tempString.getValue().released(e.getKeyCode());
		}
	}

}
