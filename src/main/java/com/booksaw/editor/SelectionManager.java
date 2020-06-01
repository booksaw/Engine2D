package main.java.com.booksaw.editor;

import java.util.ArrayList;
import java.util.List;

import main.java.com.booksaw.Engine2D.objects.GameObject;
import main.java.com.booksaw.editor.panels.GameObjectList;
import main.java.com.booksaw.editor.panels.ObjectModifierPanel;

/**
 * This class is used to manage what objects are selected at the moment
 * 
 * @author booksaw
 *
 */
public class SelectionManager {

	private static List<GameObject> selected = new ArrayList<>();

	/**
	 * Used to get the selection, will return null if no object is selected, or more
	 * than one is selected
	 */
	static public GameObject getSelection() {
		if (selected.size() != 1) {
			return null;
		}
		return selected.get(0);
	}

	static public List<GameObject> getSelected() {
		return selected;
	}

	/**
	 * This is used to select a single object (this will by default update the object list tree)
	 * @param object the object to select
	 */
	static public void select(GameObject object) {
		select(object, true);
	}

	/**
	 * This is used to select a single object
	 * @param object the object to select
	 * @param updateTree if the object list tree should be updated 
	 */
	static public void select(GameObject object, boolean updateTree) {
		clearSelection();
		object.isSelected = true;
		selected.add(object);
		if (updateTree) {
			GameObjectList.gameObjectList.setObject(object);
		}

		ObjectModifierPanel.modifierPanel.update();
	}

	static public void clearSelection() {
		for (GameObject object : selected) {
			object.isSelected = false;
		}

		selected = new ArrayList<>();

		ObjectModifierPanel.modifierPanel.update();
	}

}
