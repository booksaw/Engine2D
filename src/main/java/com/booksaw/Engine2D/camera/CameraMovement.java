package main.java.com.booksaw.Engine2D.camera;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.exception.ClassTypeMismatchException;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.objects.movement.Movement;

public abstract class CameraMovement implements Updateable {

	/**
	 * Used so all camera movement types can be tracked
	 */
	private static HashMap<String, Class<?>> movementTypes;

	/**
	 * Used to add a movement to the list of movements
	 * 
	 * @param reference     the reference for the movement
	 * @param movementClass the movement class to add
	 * @throws ClassTypeMismatchException thrown if the class provided does not
	 *                                    extends CameraMovement
	 */
	public static void addMovement(String reference, Class<?> movementClass) throws ClassTypeMismatchException {

		if (CameraMovement.class.isAssignableFrom(movementClass)) {
			movementTypes.put(reference, movementClass);
			return;
		}
		throw new ClassTypeMismatchException(Movement.class);
	}

	static {
		movementTypes = new HashMap<>();
		addMovement("auto", AutoCamera.class);
		addMovement("static", StaticCamera.class);
	}

	/**
	 * Used to create an object from the provided node
	 * 
	 * @param node the details about that object
	 */
	public static CameraMovement createObjectFromData(GameManager manager, Node node) {
		Class<?> theClass = movementTypes.get(Utils.getTagString("type", (Element) node));

		// an error has occurred
		Logger.Log("Creating Object: " + theClass);
		if (movementTypes == null) {
			return null;
		}

		try {
			Constructor<?> construct = theClass
					.getDeclaredConstructor(new Class[] { GameManager.class, Element.class });
			CameraMovement movement = (CameraMovement) construct.newInstance(manager, (Element) node);
			return movement;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Logger.Log(LogType.ERROR, "Could not create an instance of the class: " + theClass);
			Logger.Log(LogType.ERROR, e.toString());
			Logger.Log(LogType.ERROR, e.getStackTrace() + "");
			return null;
		}
	}

	protected GameManager manager;
	private int id;

	public CameraMovement(GameManager manager, Element info) {
		id = Utils.getTagInteger("id", info);
		this.manager = manager;
	}

	public int getID() {
		return id;
	}

	public GameManager getManager() {
		return manager;
	}

	public void save(Element movementEle, Document document) {
		Utils.saveValue("id", document, movementEle, id + "");

		saveValues(movementEle, document);
	}

	public abstract void saveValues(Element movementEle, Document document);

}
