package main.java.test.com.booksaw.platformer2D;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.objects.ImageObject;
import main.java.com.booksaw.Engine2D.rendering.animation.Animation;
import main.java.com.booksaw.Engine2D.rendering.animation.AnimationManager;

public class PlatformGameManager extends GameManager {

	@Override
	public void initScreen() {
//		renderManager.addComponent(new RenderTest(), true);
		try {
			Animation animation = new Animation(ImageIO.read(new File("animationTest.png")),
					"platformer2D.testAnimation", 3, 100);

			animation.saveAnimation(new File("platformer2D"));

			AnimationManager manager = new AnimationManager(animation);
			ImageObject object = new ImageObject(manager, this);
			object.x = 10;
			object.y = 10;
			object.width = 100;
			object.height = 100;
			renderManager.addComponent(object, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
