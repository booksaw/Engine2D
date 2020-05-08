package main.java.com.booksaw.Engine2D.objects.movement;

import main.java.com.booksaw.Engine2D.Vector;
import main.java.com.booksaw.Engine2D.objects.Sprite;

public class GravityMovement extends Movement {

	protected static String reference = "gravity";

	public static String getStaticReference() {
		return reference;
	}

	Sprite sprite;

	public GravityMovement(Sprite sprite, String information) {
		super(sprite, information);
		this.sprite = sprite;
	}

	@Override
	public void update(Vector velocity) {
		if (!sprite.getManager().level.isColliding(sprite.getShape(new Vector(0, -1)), sprite)) {
			velocity.applyVector(new Vector(0, sprite.getManager().accelerationGravity), -1, sprite.getMaxSpeedY(),
					sprite.getMaxSpeed(), false);
		}
	}

	@Override
	public String getOutput() {
		return "0";
	}

	@Override
	public String getReference() {
		return reference;
	}

}
