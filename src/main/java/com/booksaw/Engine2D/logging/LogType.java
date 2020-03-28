package main.java.com.booksaw.Engine2D.logging;

/**
 * Used to specify the level of a logged event
 * 
 * @author booksaw
 *
 */
public enum LogType {
	INFO(1), ERROR(3), WARNING(2);

	/**
	 * The level of logging used (used to calculate if the message should get
	 * through)
	 */
	private int level;

	/**
	 * @param level the
	 */
	private LogType(int level) {
		this.level = level;
	}

	/**
	 * @return the level of logging which that type of message has
	 */
	public int getLevel() {
		return level;
	}

}
