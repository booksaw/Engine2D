package main.java.com.booksaw.Engine2D.logging;

/**
 * This class is used for logging information about the progress of the program
 * 
 * @author booksaw
 *
 */
public class Logger {

	/**
	 * Used to specify the current level of logging, can be used to aid with
	 * debugging
	 */
	public static LogType minimumToLog = LogType.INFO;

	/**
	 * This can be used to locate logs which are no longer required (each log prints
	 * its caller location)
	 */
	public static boolean locate = true;

	/**
	 * Used to create non-permanent debug logs quickly
	 * 
	 * @param message
	 */
	public static void Log(String message) {
		Log(LogType.INFO, message);
	}

	/**
	 * Main logging command, used to log any useful information gathered
	 * 
	 * @param type    the priority level of the log
	 * @param message the message to log
	 */
	public static void Log(LogType type, String message) {
		if (minimumToLog.getLevel() <= type.getLevel()) {
			if (!locate)
				System.out.printf("[%s] %s\n", type.toString(), message);
			else {
				// used to trace where the call has come from
				StackTraceElement ele = Thread.currentThread().getStackTrace()[3];
				System.out.printf("[%s] %s (%s, %d)\n", type.toString(), message, ele.getClassName(),
						ele.getLineNumber());
			}
		}

	}

}
