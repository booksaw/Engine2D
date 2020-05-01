package main.java.test.com.booksaw.platformer2D;

import java.io.File;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.objects.Sprite;
import main.java.com.booksaw.Engine2D.rendering.animation.Animation;
import main.java.com.booksaw.Engine2D.rendering.animation.AnimationManager;

public class PlatformGameManager extends GameManager {

	@Override
	public void initScreen() {
//		renderManager.addComponent(new RenderTest(), true);
//		try {
		Animation animation = Animation.loadAnimation(new File("platformer2D" + File.separator + "animation"),
				"platformer2D.testAnimation");

		AnimationManager manager = new AnimationManager(animation);
		Sprite object = new Sprite(manager, this, 1);
		object.x = 10;
		object.y = 10;
		object.width = 100;
		object.height = 100;
		renderManager.addComponent(object, true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
