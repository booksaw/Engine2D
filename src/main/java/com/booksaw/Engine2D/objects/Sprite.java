package main.java.com.booksaw.Engine2D.objects;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Utils;
import main.java.com.booksaw.Engine2D.modifiers.type.DoubleModifier;
import main.java.com.booksaw.Engine2D.modifiers.type.IntegerModifier;
import main.java.com.booksaw.Engine2D.objects.movement.Movement;
import main.java.com.booksaw.Engine2D.rendering.animation.AnimationManager;

/**
 * Class for moving objects
 * 
 * @author booksaw
 *
 */
public class Sprite extends ImageObject {

	protected static String reference = "sprite";

	public static String getStaticReference() {
		return reference;
	}

	private List<Movement> moveSet;

	public Sprite(AnimationManager manager, GameManager gameManager, int player) {
		super(manager, gameManager);
		moveSet = new ArrayList<>();
	}

	public Sprite(GameManager manager, Element details) {
		super(manager, details);

		addModifier(details, "player", "Player number (for determining controls)", new IntegerModifier());
		addModifier(details, "maxSpeedX", "Maximum horizonal speed", new DoubleModifier());
		addModifier(details, "maxSpeedY", "Maximum vertical speed", new DoubleModifier());
		addModifier(details, "maxSpeed", "Maximum speed", new DoubleModifier());

		moveSet = new ArrayList<>();
		// looping through every animation
		for (int i = 0; i < (Utils.getTagInteger("maxMovement", details)); i++) {
			moveSet.add(Movement.createObjectFromData(this, Utils.getTagString("movement_" + i, details)));
		}

	}

	@Override
	public void save(Element element, Document document) {
		super.save(element, document);
		// looping through every animation
		int i = 0;
		for (Movement movement : moveSet) {
			Utils.saveValue("movement_" + i, document, element, movement.getReference() + ";" + movement.getOutput());
			i++;
		}
		Utils.saveValue("maxMovement", document, element, moveSet.size() + "");
	}

	@Override
	public void update(int time) {
		super.update(time);
		move(time);
	}

	/**
	 * A simple movement script so players can control an object
	 * 
	 * @param time the number of ticks since last update
	 */
	public void move(int time) {
		// running each tick individually
		for (int i = 0; i < time; i++) {
			for (Movement m : moveSet) {
				m.update(velocity);
			}

		}

	}

	/**
	 * Used to add a movement type to this sprite
	 * 
	 * @param m the movement type to add
	 */
	public void addMovement(Movement m) {
		moveSet.add(m);
	}

	/**
	 * Used to remove a movement type from this sprite
	 * 
	 * @param m the movement type to remove
	 */
	public void removeMovement(Movement m) {
		moveSet.remove(m);
	}

	/**
	 * Used to get a list of all for this sprite
	 * 
	 * @return the list
	 */
	public List<Movement> getMoveSet() {
		return moveSet;
	}

	@Override
	public String getReference() {
		return reference;
	}

	public int getPlayer() {
		return getModifier("player").getIntValue();
	}

	public double getMaxSpeedX() {
		return getModifier("maxSpeedX").getDoubleValue();
	}

	public double getMaxSpeedY() {
		return getModifier("maxSpeedY").getDoubleValue();
	}

	public double getMaxSpeed() {
		return getModifier("maxSpeed").getDoubleValue();
	}

}
