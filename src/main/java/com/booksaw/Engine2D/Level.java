package main.java.com.booksaw.Engine2D;

import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.java.com.booksaw.Engine2D.collision.CollisionManager;
import main.java.com.booksaw.Engine2D.exception.ClassTypeMismatchException;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.objects.GameObject;
import main.java.com.booksaw.Engine2D.objects.ImageObject;
import main.java.com.booksaw.Engine2D.objects.Sprite;
import main.java.com.booksaw.Engine2D.rendering.RenderedComponent;

public class Level {

	/**
	 * Used so all game objects can be tracked
	 */
	private static HashMap<String, Class<?>> gameObjectTypes;

	/**
	 * Adding all default game objects
	 */
	static {
		gameObjectTypes = new HashMap<>();
		gameObjectTypes.put(ImageObject.getReference(), ImageObject.class);
		gameObjectTypes.put(Sprite.getReference(), Sprite.class);

	}

	/**
	 * Used to add a custom game object into the list of possible game objects
	 * 
	 * @param gameObjectClass the game object class to add
	 * @param reference       the reference of the game object (use
	 *                        gameObject.reference to fetch)
	 * @throws ClassTypeMismatchException this is thrown if the class is does not
	 *                                    extend the superclass GameObject
	 */
	public static void addGameObject(String reference, Class<?> gameObjectClass) throws ClassTypeMismatchException {

		if (GameObject.class.isAssignableFrom(gameObjectClass)) {
			gameObjectTypes.put(reference, gameObjectClass);
		}
		throw new ClassTypeMismatchException(GameObject.class);
	}

	/**
	 * Used to store the game manager, so when objects are created, they can be
	 * added to the render manager
	 */
	private GameManager manager;

	List<GameObject> objects;

	public Level(GameManager manager, File data) {
		this.manager = manager;
		objects = new ArrayList<>();
		Logger.Log(LogType.INFO, "Loading Level");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(data);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("object");
			Logger.Log(LogType.INFO, "Loading " + nodeList.getLength() + " objects.");
			objects = new ArrayList<GameObject>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				createObjectFromData(nodeList.item(i));

			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			Logger.Log(LogType.ERROR, "Could not load level file " + data);
		}

	}

	private void createObjectFromData(Node node) {
		Class<?> theClass = gameObjectTypes.get(Utils.getTagValue("type", (Element) node));

		try {
			Constructor<?> construct = theClass
					.getDeclaredConstructor(new Class[] { GameManager.class, Element.class });
			construct.newInstance(manager, (Element) node);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Logger.Log(LogType.ERROR, "Could not create an instance of the class: " + theClass);
			Logger.Log(e.toString());
		}
	}

	/**
	 * Used to check if an object is colliding with anything
	 * 
	 * @param shape  the shape to check if it is colliding with anything
	 * @param ignore the object to ignore (so it does not collide with itself)
	 * @return
	 */
	public boolean isColliding(Shape shape, GameObject ignore) {
		for (GameObject object : objects) {
			if (object != ignore && CollisionManager.isColliding(shape, object.getShape())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * This is used to start rendering and updating the level (this is not done at
	 * the start, so the level can be pre-loaded)
	 */
	public void activateLevel() {
		for (GameObject object : objects) {
			manager.renderManager.addComponent((RenderedComponent) object, false);
			// starting the udpates
			if (object instanceof Updateable) {
				manager.addUpdatable((Updateable) object);
			}
		}
		// sorting the list
		manager.renderManager.sortComponents();

	}

	/**
	 * Used to stop the level running once it is no longer needed to be rendered
	 */
	public void deactivateLevel() {
		for (GameObject object : objects) {
			manager.renderManager.removeComponent((RenderedComponent) object);
			// stopping the updates
			if (object instanceof Updateable) {
				manager.removeUpdatable((Updateable) object);
			}
		}
		// sorting the list
		manager.renderManager.sortComponents();
	}

}
