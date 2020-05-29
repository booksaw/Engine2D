package main.java.com.booksaw.Engine2D.camera;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * This camera is to be used when the camera stays in a single location
 * 
 * @author nfgg2
 *
 */
public class StaticCamera extends CameraMovement {

	public StaticCamera(GameManager manager, Element info) {
		super(manager, info);
	}

	/**
	 * This is an additional constructor which is used when a new level is created
	 * 
	 * @param manager
	 */
	public StaticCamera(GameManager manager, int id) {
		super(manager, null);
		Logger.Log(LogType.WARNING,
				"Expected lack of ID for camera. (Expected to only be seen when a new level is being created)");
		this.id = id;
	}

	@Override
	public void update(int time) {
	}

	@Override
	public void saveValues(Element movementEle, Document document) {
	}

	@Override
	public String getType() {
		return "static";
	}

}
