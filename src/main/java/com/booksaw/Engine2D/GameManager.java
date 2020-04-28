package main.java.com.booksaw.Engine2D;

import main.java.com.booksaw.Engine2D.objects.Camera;
import main.java.com.booksaw.Engine2D.rendering.Engine2DFrame;
import main.java.com.booksaw.Engine2D.rendering.RenderClock;
import main.java.com.booksaw.Engine2D.rendering.RenderManager;

/**
 * The class to manage a game screen, this will manage everything from the
 * rendering components to updating all the components within the game. Programs
 * using this game engine should extend this class to improve the management of
 * the game
 * 
 * @author booksaw
 *
 */
public abstract class GameManager {

	/**
	 * This is used to track if this is the game which is being currently rendered
	 */
	private boolean rendering;

	/**
	 * This is used to store the rendering clock which is re-rendering the screen
	 */
	private RenderClock clock;

	/**
	 * The camera controlling the viewport
	 */
	public Camera camera;

	/**
	 * This stores the object which manages all information for the game
	 */
	protected RenderManager renderManager;

	/**
	 * Used to setup the program, do not override in sub programs, but instead use
	 * the initScreen method to carry out any set up
	 */
	public GameManager() {

		rendering = false;
		clock = new RenderClock(this);
		renderManager = new RenderManager(this);
		// TODO need to make more specific for different camera dimensions
		camera = new Camera(0, 0, 700, 400, 700, 400);

		// the specific game setup
		initScreen();
	}

	/**
	 * This method is run when the game manager is initially created, and should be
	 * used to setup the level as seen appropriate
	 */
	public abstract void initScreen();

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
		clock.setActive(rendering);

		if (rendering) {
			// setting the frame to display the correct content
			Engine2DFrame.setActiveRender(renderManager);
		}

	}

	/**
	 * 
	 * @return the render manager for this game
	 */
	public RenderManager getRenderManager() {
		return renderManager;
	}

}
