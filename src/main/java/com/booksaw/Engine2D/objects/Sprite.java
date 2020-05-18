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

/**
 * Class for moving objects
 * 
 * @author booksaw
 *
 */
public class Sprite extends ImageObject {

	/**
	 * The unique reference for this object type
	 */
	protected static String reference = "sprite";

	/**
	 * Used to get the unique reference in a static way
	 * 
	 * @return the unique reference for this class
	 */
	public static String getStaticReference() {
		return reference;
	}

	/**
	 * A list of all the movement types to be applied to this sprite
	 */
	private List<Movement> moveSet;

	/**
	 * Used to load a sprite from file
	 * 
	 * @param manager the game manager which is creating the sprite
	 * @param details the reference to the details about this gameObject
	 */
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

	/**
	 * Used to create a new sprite with default settings
	 * 
	 * @param manager the game manager which is creating the sprite
	 */
	public Sprite(GameManager manager) {
		super(manager);
		addModifier("player", "Player number (for determining controls)", 1, new IntegerModifier());
		addModifier("maxSpeedX", "Maximum horizontal speed", "-1", new DoubleModifier());
		addModifier("maxSpeedY", "Maximum vertical speed", "-1", new DoubleModifier());
		addModifier("maxSpeed", "Maximum speed", "-1", new DoubleModifier());
		moveSet = new ArrayList<>();
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

	/**
	 * This is used to check which player controls tihs sprite is using
	 * 
	 * @return which player the sprite is
	 */
	public int getPlayer() {
		return getModifier("player").getIntValue();
	}

	/**
	 * @return The maximum speed in the x direction
	 */
	public double getMaxSpeedX() {
		return getModifier("maxSpeedX").getDoubleValue();
	}

	/**
	 * @return the maximum speed in the y direction
	 */
	public double getMaxSpeedY() {
		return getModifier("maxSpeedY").getDoubleValue();
	}

	/**
	 * @return the maximum modulus speed
	 */
	public double getMaxSpeed() {
		return getModifier("maxSpeed").getDoubleValue();
	}

}
