package main.java.com.booksaw.Engine2D.objects.movement;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.objects.Sprite;

public class GravityMovement extends Movement {

	Sprite sprite;

	public GravityMovement(Sprite sprite, String information) {
		super(sprite, information);
		this.sprite = sprite;
	}

	@Override
	public void update(Vector velocity) {
		velocity.applyVector(new Vector(0, sprite.getManager().accelerationGravity), -1, sprite.maxSpeedY,
				sprite.maxSpeed, false);
	}

	@Override
	public String getOutput() {
		return "";
	}

}
