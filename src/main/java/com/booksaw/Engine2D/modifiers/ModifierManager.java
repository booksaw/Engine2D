package main.java.com.booksaw.Engine2D.modifiers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * This class is used to manage all modifiers so they are correctly handled
 * 
 * @author nfgg2
 *
 */
public class ModifierManager {

	/**
	 * Used to store a list of all modifiers
	 */
	private static HashMap<String, Modifier> modifiers;

	/**
	 * Used to load the modifiers
	 */
	public static void loadModifiers() {
		File f = new File("Engine2D" + File.separator + "modifiers");
		if (!f.exists()) {
			Logger.Log(LogType.WARNING, "Modifier file does not exist, creating one...");
			try {
				f.createNewFile();
			} catch (Exception e) {

			}
		}

		// reading the modifiers
		try {
			modifiers = new HashMap<>();
			BufferedReader reader = new BufferedReader(new FileReader(f));

			// adding all modifiers
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(":");
				modifiers.put(split[0], new Modifier(split[0], split[1], split[2]));
			}

			reader.close();

		} catch (Exception e) {
			Logger.Log(LogType.ERROR, "Could not load modifiers file");
		}

	}

	/**
	 * Used to save the modifiers
	 */
	public static void saveModifiers() {
		File f = new File("Engine2D" + File.separator + "modifiers");
		if (!f.exists()) {
			Logger.Log(LogType.WARNING, "Modifier file does not exist, creating one...");
			try {
				f.createNewFile();
			} catch (Exception e) {

			}
		}
		try {
			PrintWriter writer = new PrintWriter(f);

			for (Entry<String, Modifier> temp : modifiers.entrySet()) {
				writer.println(temp.getValue());
			}

			writer.close();

		} catch (Exception e) {
			Logger.Log(LogType.ERROR, "Could not save modifiers file");
		}

	}

	public static Modifier getModifier(String reference) {
		if (modifiers == null) {
			loadModifiers();
		}
		return modifiers.get(reference);

	}

	public static void addModifier(Modifier modifier) {
		if (modifiers == null) {
			loadModifiers();
		}

		modifiers.put(modifier.reference, modifier);
	}

	public static void addModifier(String reference) {
		if (modifiers == null) {
			loadModifiers();
		}

		modifiers.remove(reference);
	}

}
