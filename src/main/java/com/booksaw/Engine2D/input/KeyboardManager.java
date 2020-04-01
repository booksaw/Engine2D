package main.java.com.booksaw.Engine2D.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

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

	/**
	 * Used to clear all pressed keys (every key will need removing and re-pressing
	 * for it to be activated)
	 */
	public void purge() {
		// purging all individual
		for (Entry<String, KeyMapping> temp : keyMappings.entrySet()) {
			temp.getValue().purge();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// unneeded, as pressed and released cover all needs required
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// updating the key mappings
		for (Entry<String, KeyMapping> temp : keyMappings.entrySet()) {
			temp.getValue().pressed(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// updating the key mappings
		for (Entry<String, KeyMapping> temp : keyMappings.entrySet()) {
			temp.getValue().released(e.getKeyCode());
		}
	}

	/**
	 * Used to check if a trigger has been activated
	 * 
	 * @param reference
	 * @return
	 */
	public boolean isActive(String reference) {
		return keyMappings.get(reference).isPressed();
	}

	/**
	 * Used to save all current key mappings to the specified file
	 * 
	 * @param file the file to save the key mappings to (anything inside will be
	 *             overwritten)
	 */
	public void save(File file) {

		// checking if the file exists
		if (!file.exists()) {
			Logger.Log(LogType.ERROR, "Tried to save KeyMappings but the file provided did not exist");
			return;
		}

		// making the print writer
		try {
			PrintWriter pw = new PrintWriter(file);

			for (Entry<String, KeyMapping> temp : keyMappings.entrySet()) {
				// toString returns the values in a string format
				pw.println(temp.getValue().toString());
			}

			pw.close();
		} catch (Exception e) {
			Logger.Log(LogType.ERROR, "Tried to save KeyMappings but an error occurred");
			return;
		}

	}

	/**
	 * Used to load a set of key mappings from the specified file
	 * 
	 * @param file the file in which the key mappings are stored in
	 */
	public void load(File file) {
		// checking if the file exists
		if (!file.exists()) {
			Logger.Log(LogType.ERROR, "Tried to save KeyMappings but the file provided did not exist");
			return;
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";

			// looping through every line
			while ((line = reader.readLine()) != null) {
				KeyMapping mapping = new KeyMapping(line);
				keyMappings.put(mapping.getReference(), mapping);
			}

			reader.close();

		} catch (Exception e) {
			Logger.Log(LogType.ERROR, "Tried to save KeyMappings but an error occurred");
			return;

		}

	}

}
