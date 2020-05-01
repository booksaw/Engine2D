package main.java.com.booksaw.Engine2D;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import main.java.com.booksaw.Engine2D.collision.CollisionManager;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.Engine2D.objects.GameObject;
import main.java.com.booksaw.Engine2D.rendering.RenderedComponent;

public class Level {

	/**
	 * Used to store the game manager, so when objects are created, they can be
	 * added to the render manager
	 */
	private GameManager manager;

	public Level(GameManager manager) {
		this.manager = manager;
		objects = new ArrayList<>();
	}

	List<GameObject> objects;

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
