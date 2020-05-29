package main.java.com.booksaw.Engine2D.camera;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;

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

	@Override
	public void update(int time) {
	}

	@Override
	public void saveValues(Element movementEle, Document document) {
	}

}
