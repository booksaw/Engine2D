package main.java.com.booksaw.Engine2D;

import java.awt.Dimension;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.java.com.booksaw.Engine2D.camera.CameraMovement;
import main.java.com.booksaw.Engine2D.collision.CollisionManager;
import main.java.com.booksaw.Engine2D.exception.ClassTypeMismatchException;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.objects.ColorObject;
import main.java.com.booksaw.Engine2D.objects.GameObject;
import main.java.com.booksaw.Engine2D.objects.ImageObject;
import main.java.com.booksaw.Engine2D.objects.Sprite;
import main.java.com.booksaw.Engine2D.rendering.RenderedComponent;

public class Level implements Updateable {

	/**
	 * Used so all game objects can be tracked
	 */
	private static HashMap<String, Class<?>> gameObjectTypes;

	/**
	 * @return a HashMap of all possible game object types (registered sub classes
	 *         of gameObject)
	 */
	public static HashMap<String, Class<?>> getGameObjectTypes() {
		return gameObjectTypes;
	}

	/**
	 * Adding all default game objects
	 */
	static {
		gameObjectTypes = new HashMap<>();
		gameObjectTypes.put(ImageObject.getStaticReference(), ImageObject.class);
		gameObjectTypes.put(Sprite.getStaticReference(), Sprite.class);
		gameObjectTypes.put(ColorObject.getStaticReference(), ColorObject.class);

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
			return;
		}
		throw new ClassTypeMismatchException(GameObject.class);
	}

	/**
	 * Used to store the game manager, so when objects are created, they can be
	 * added to the render manager
	 */
	private GameManager manager;

	/**
	 * Used to store a list of all objects in this level
	 */
	private List<GameObject> objects;

	/**
	 * Used to store a list of all the camera movements for this level
	 */
	private HashMap<Integer, CameraMovement> cameraMovements;

	/**
	 * Used to store which camera movement is currently being used
	 */
	private CameraMovement activeCameraMovement;

	/**
	 * Stores if the level is currently active or not
	 */
	private boolean active = false;

	/**
	 * The XML file in which all the data is stored
	 */
	private File data;

	/**
	 * Used to load a level
	 * 
	 * @param manager the game manager which is running the level
	 * @param data    the data associated with the level
	 */
	public Level(GameManager manager, File data) {
		this.data = data;
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

			nodeList = doc.getElementsByTagName("cameraMove");
			Logger.Log(LogType.INFO, "Loading " + nodeList.getLength() + " cameraMove.");
			cameraMovements = new HashMap<>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				CameraMovement movement = CameraMovement.createObjectFromData(manager, nodeList.item(i));
				if (movement != null) {
					addCameraMovement(movement);
				}
			}

			Node node = doc.getElementById("settings");
			Logger.Log(LogType.INFO, "Loading " + nodeList.getLength() + " cameraMove.");
			activeCameraMovement = getCameraMovement(Utils.getTagInteger("activeCameraMovement", (Element) node));

		} catch (SAXException | ParserConfigurationException | IOException e1) {
			Logger.Log(LogType.ERROR, "Could not load level file " + data);
		}
		levelDimensions = new Dimension(1000, 1000);
	}

	/**
	 * The level dimensions (objects should not leave these dimensions unless for
	 * good reason)
	 */
	private Dimension levelDimensions;

	/**
	 * Used to create an object from the provided node
	 * 
	 * @param node the details about that object
	 */
	private void createObjectFromData(Node node) {
		Class<?> theClass = gameObjectTypes.get(Utils.getTagString("type", (Element) node));

		// an error has occurred
		Logger.Log("Creating Object: " + theClass);
		if (gameObjectTypes == null) {
			return;
		}

		try {
			Constructor<?> construct = theClass
					.getDeclaredConstructor(new Class[] { GameManager.class, Element.class });
			GameObject object = (GameObject) construct.newInstance(manager, (Element) node);
			addObject(object);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Logger.Log(LogType.ERROR, "Could not create an instance of the class: " + theClass);
			Logger.Log(LogType.ERROR, e.toString());
			Logger.Log(LogType.ERROR, e.getStackTrace() + "");
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
	 * Used to get the object that is being collided with
	 * 
	 * @param shape
	 * @param ignore
	 * @return
	 */
	public GameObject getColliding(Shape shape, GameObject ignore) {
		for (GameObject object : objects) {
			if (object != ignore && CollisionManager.isColliding(shape, object.getShape())) {
				return object;
			}
		}

		return null;
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
		manager.addUpdatable(this);

		// sorting the list
		manager.renderManager.sortComponents();
		active = true;
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
		manager.removeUpdatable(this);

		// sorting the list
		manager.renderManager.sortComponents();
		active = false;
	}

	/**
	 * Used to add an object to the game
	 */
	public void addObject(GameObject object) {
		objects.add(object);
		if (active) {
			if (object instanceof Updateable) {
				manager.addUpdatable((Updateable) object);
			}
			manager.getRenderManager().addComponent(object, true);
		}
	}

	/**
	 * Used to check if the level is currently rendering / updating
	 * 
	 * @return if the level is currently rendering /updating
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Used to save level details to file
	 * 
	 */
	public void saveLevel() {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();
			// making the main branch
			Element level = document.createElement("level");
			document.appendChild(level);

			for (GameObject object : objects) {
				Element objectEle = document.createElement("object");
				object.save(objectEle, document);
				level.appendChild(objectEle);

			}

			for (Entry<Integer, CameraMovement> movement : cameraMovements.entrySet()) {
				Element movementEle = document.createElement("cameraMove");
				movement.getValue().save(movementEle, document);
				level.appendChild(movementEle);

			}

			Element settings = document.createElement("settings");
			Utils.saveValue("activeCameraMovement", document, settings, activeCameraMovement.getID() + "");
			level.appendChild(settings);

			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(data);

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging

			transformer.transform(domSource, streamResult);

		} catch (ParserConfigurationException | TransformerException pce) {
			Logger.Log(LogType.ERROR, "Could not save level data to file " + data);
			Logger.Log(LogType.ERROR, pce.toString());
		}
	}

	/**
	 * Moves each object back to the starting position
	 */
	public void reset() {
		for (GameObject object : objects) {
			object.reset();
		}
		for (GameObject object : objects) {
			object.checkCollisionOnReset();
		}
	}

	public List<GameObject> getGameObjects() {
		return objects;
	}

	/**
	 * Used to get the object with the specified ID
	 * 
	 * @param ID the id
	 * @return the object
	 */
	public GameObject getObject(String ID) {
		return getObject(ID, null);
	}

	/**
	 * Used to get the object with the specified ID. this version of the method is
	 * mainly used when generating IDs
	 * 
	 * @param ID     the ID
	 * @param ignore the object to ignore
	 * @return the object
	 */
	public GameObject getObject(String ID, GameObject ignore) {
		for (GameObject object : objects) {
			if (object != ignore && object.toString().equals(ID)) {
				return object;
			}
		}
		return null;
	}

	public Dimension getLevelDimensions() {
		return levelDimensions;
	}

	public void setLevelDimensions(Dimension levelDimensions) {
		this.levelDimensions = levelDimensions;
	}

	public void addCameraMovement(CameraMovement movement) {
		cameraMovements.put(movement.getID(), movement);
	}

	public CameraMovement getCameraMovement(int id) {
		return cameraMovements.get(id);
	}

	@Override
	public void update(int time) {
		if (activeCameraMovement != null) {
			activeCameraMovement.update(time);
		}
	}

}
