package main.java.com.booksaw.Engine2D.camera;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Utils;

/**
 * This camera will move a set distance per tick, the speed of the camera can be
 * specified when creating the camera
 * 
 * @author booksaw
 *
 */
public class AutoCamera extends CameraMovement {

	private double speedX, speedY;

	public AutoCamera(GameManager manager, Element details) {
		super(manager, details);
		speedX = Utils.getTagDouble("x", details);
		speedY = Utils.getTagDouble("y", details);
	}

	@Override
	public void update(int time) {
		manager.camera.x += speedX;
		manager.camera.y += speedY;
	}

	@Override
	public void saveValues(Element movementEle, Document document) {
		Utils.saveValue("x", document, movementEle, speedX + "");
		Utils.saveValue("y", document, movementEle, speedY + "");

	}

}
