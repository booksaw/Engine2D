package main.java.com.booksaw.editor;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * This class is used to control the camera when the level editor is being
 * shown, this is used to move around the camera
 * 
 * @author booksaw
 *
 */
public class CameraController implements Updateable {

	GameManager manager;
	private final int speed = 5;

	public CameraController(GameManager manager) {
		Logger.Log("Adding");
		Editor2DFrame.clock.addUpdatable(this);
		this.manager = manager;
	}

	@Override
	public void update(int time) {
		if (KeyboardManager.keyboardManager.isActive("player.1.up")) {
			manager.camera.y += speed * time;
			if (manager.camera.y + manager.camera.height > manager.level.getLevelDimensions().height) {
				manager.camera.y = manager.level.getLevelDimensions().height - manager.camera.height;
			}
		}
		if (KeyboardManager.keyboardManager.isActive("player.1.down")) {
			manager.camera.y -= speed * time;
			if (manager.camera.y < 0) {
				manager.camera.y = 0;
			}
		}
		if (KeyboardManager.keyboardManager.isActive("player.1.left")) {
			manager.camera.x -= speed;
			if (manager.camera.x < 0) {
				manager.camera.x = 0 * time;
			}
		}
		if (KeyboardManager.keyboardManager.isActive("player.1.right")) {
			manager.camera.x += speed * time;
			if (manager.camera.x + manager.camera.width > manager.level.getLevelDimensions().width) {
				manager.camera.x = manager.level.getLevelDimensions().width - manager.camera.width;
			}
		}

	}

	public void remove() {
		Editor2DFrame.clock.removeUpdatable(this);
	}

}
