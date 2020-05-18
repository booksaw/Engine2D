package main.java.com.booksaw.Engine2D.modifiers.type;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.modifiers.Modifier;
import main.java.com.booksaw.Engine2D.objects.GameObject;

/**
 * This class is used for selecting ID modifiers in the editor, it ensires the
 * id of the object is unique
 * 
 * @author booksaw
 *
 */
public class ObjectIDModifier extends StringModifier {

	private transient GameManager manager;
	private transient GameObject object;

	public ObjectIDModifier(GameManager manager, GameObject object) {
		this.manager = manager;
		this.object = object;
	}

	@Override
	public void handleInput(Modifier modifier, JComponent component) {
		JTextArea area = (JTextArea) component;
		// checking if it already exists
		if (manager.level.getObject(area.getText(), object) == null) {
			modifier.setValue(area.getText());
		}

	}

}
