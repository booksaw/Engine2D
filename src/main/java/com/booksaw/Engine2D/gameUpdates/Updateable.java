package main.java.com.booksaw.Engine2D.gameUpdates;

/**
 * Used for any object which needs updating (will be updated each game tick)
 * 
 * @author booksaw
 *
 */
public interface Updateable {

	/**
	 * This method is used to update the object once per tick (best case scnario)
	 * 
	 * @param time the the number of ticks which have passed since last update (used
	 *             for when the clock runs slower than expected)
	 */
	public void update(int time);

}
