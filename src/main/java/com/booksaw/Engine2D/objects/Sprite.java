package main.java.com.booksaw.Engine2D.objects;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.Utils;
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

	public int player;
	public double maxSpeedX = 2.5, maxSpeedY = -1, maxSpeed = -1;
	private List<Movement> moveSet;

	public Sprite(AnimationManager manager, GameManager gameManager, int player) {
		super(manager, gameManager);
		this.player = player;
		moveSet = new ArrayList<>();
	}

	public Sprite(GameManager manager, Element details) {
		super(manager, details);

		player = Utils.getTagInteger("player", details);
		maxSpeedX = Utils.getTagDouble("maxSpeedX", details);
		maxSpeedY = Utils.getTagDouble("maxSpeedY", details);
		maxSpeed = Utils.getTagDouble("maxSpeed", details);

		moveSet = new ArrayList<>();
		// looping through every animation
		for (int i = 0; i < (Utils.getTagInteger("maxMovement", details)); i++) {
			moveSet.add(Movement.createObjectFromData(this, Utils.getTagString("movement_" + i, details)));
		}

	}

	@Override
	public void save(Element element, Document document) {
		super.save(element, document);
		Utils.saveValue("player", document, element, player + "");
		Utils.saveValue("maxSpeedX", document, element, maxSpeedX + "");
		Utils.saveValue("maxSpeedY", document, element, maxSpeedY + "");
		Utils.saveValue("maxSpeed", document, element, maxSpeed + "");

		Utils.saveValue("maxMovement", document, element, moveSet.size() + "");
		// looping through every animation
		int i = 0;
		for (Movement movement : moveSet) {
			Utils.saveValue("movement_" + i, document, element, movement.getOutput());
			i++;
		}
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

}
