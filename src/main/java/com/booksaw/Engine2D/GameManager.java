package main.java.com.booksaw.Engine2D;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.java.com.booksaw.Engine2D.camera.Camera;
import main.java.com.booksaw.Engine2D.gameUpdates.UpdateClock;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.Engine2D.modifiers.ModifierManager;
import main.java.com.booksaw.Engine2D.rendering.Engine2DFrame;
import main.java.com.booksaw.Engine2D.rendering.RenderClock;
import main.java.com.booksaw.Engine2D.rendering.RenderManager;
import main.java.com.booksaw.editor.panels.GamePanel;

/**
 * The class to manage a game screen, this will manage everything from the
 * rendering components to updating all the components within the game. Programs
 * using this game engine should extend this class to improve the management of
 * the game
 * 
 * @author booksaw
 *
 */
public class GameManager {

	/**
	 * This is used to track if this is the game which is being currently rendered
	 */
	private boolean rendering;

	/**
	 * This is used to store the rendering clock which is re-rendering the screen
	 */
	private RenderClock renderClock;

	/**
	 * This is used to store the update clock which is updating all components which
	 * require updating
	 */
	private UpdateClock updateClock;

	/**
	 * The camera controlling the viewport
	 */
	public Camera camera;

	/**
	 * This stores the object which manages all information for the game
	 */
	protected RenderManager renderManager;

	/**
	 * Used to store all components which need updating
	 */
	public List<Updateable> updatables = new ArrayList<>();

	/**
	 * Used to store the level which is currently loaded
	 */
	public Level level;

	/**
	 * The standard acceleration due to gravity throughout the level
	 */
	public double accelerationGravity;

	/**
	 * Used to setup the program, do not override in sub programs, but instead use
	 * the initScreen method to carry out any set up
	 * 
	 * @param level the file which stores the initial level information
	 */
	public GameManager(File level) {

		this.level = new Level(this, level);

		rendering = false;
		renderClock = new RenderClock(this);
		updateClock = new UpdateClock(CONFIG.tickLength, this);

		renderManager = new RenderManager(this);
		camera = new Camera(0, 0, ModifierManager.getModifier("engine2d.frame.prefwidth").getIntValue(),
				ModifierManager.getModifier("engine2d.frame.prefheight").getIntValue(),
				ModifierManager.getModifier("engine2d.frame.width").getIntValue(),
				ModifierManager.getModifier("engine2d.frame.height").getIntValue());

		// the specific game setup
		accelerationGravity = -0.5;
		initScreen();
	}

	/**
	 * This method is run when the game manager is initially created, and should be
	 * used to setup the level as seen appropriate
	 */
	public void initScreen() {
	}

	/**
	 * Checks if this game is rendering
	 * 
	 * @return true if the game is actively rendering
	 */
	public boolean isRendering() {
		return rendering;
	}

	/**
	 * Sets if the game is currently rendering
	 * 
	 * @param rendering if the game is rendering
	 */
	public void setRendering(boolean rendering) {
		this.rendering = rendering;
		// stopping the render clock
		renderClock.setActive(rendering);

		if (rendering) {
			// setting the frame to display the correct content
			if (Engine2DFrame.isInit()) {
				Engine2DFrame.setActiveRender(this);
			} else if (GamePanel.isInit()) {
				GamePanel.setActiveRender(this);
			}
		}

	}

	/**
	 * Used to pause the game
	 */
	public void pause() {
		pause(true);
	}

	/**
	 * Used to resume the game
	 */
	public void resume() {
		if (!level.isActive()) {
			level.activateLevel();
		}

		updateClock.setActive(true);
	}

	/**
	 * Used to pause the game
	 */
	public void pause(boolean pauseScreen) {
		updateClock.setActive(false);
	}

	/**
	 * 
	 * @return the render manager for this game
	 */
	public RenderManager getRenderManager() {
		return renderManager;
	}

	/**
	 * Used to update all components which need updating
	 */
	public void update(int time) {
		for (Updateable update : updatables) {
			update.update(time);
		}
	}

	/**
	 * Used to add an updatable to the list of all updatables in this gameManager
	 * 
	 * @param update the updatable to add
	 */
	public void addUpdatable(Updateable update) {
		updatables.add(update);
	}

	/**
	 * Used to remove an updatabale from the list of all updatables in this
	 * gameManager
	 * 
	 * @param update the updatabale to remove
	 */
	public void removeUpdatable(Updateable update) {
		updatables.remove(update);
	}

	/**
	 * @return if the game is currently being updated
	 */
	public boolean isUpdating() {
		return updateClock.isActive();
	}

	/**
	 * This method is used to set the level that the game manager is displaying
	 * 
	 * @param level
	 */
	public void setLevel(Level level) {

		this.level = level;
		level.activateLevel();

		renderManager = new RenderManager(this);

		initScreen();

		if (isRendering()) {
			setRendering(true);
		}
	}

}
