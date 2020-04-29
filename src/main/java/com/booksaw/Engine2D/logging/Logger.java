package main.java.com.booksaw.Engine2D.logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

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
	public static boolean locate = false;
	static PrintStream stream;

	/**
	 * block is used to load the default settings from file
	 */
	static {
		File f = new File("Engine2D" + File.separator + "logconfig");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));

			// first line is explanation
			reader.readLine();
			// second line is log minimum
			String info = reader.readLine();
			switch (info) {
			case "INFO":
				minimumToLog = LogType.INFO;
				break;
			case "WARNING":
				minimumToLog = LogType.WARNING;
				break;
			default:
				minimumToLog = LogType.ERROR;
			}

			// second line is if locate is on
			locate = Boolean.parseBoolean(reader.readLine());
			// third line is if the logger should write to file

			boolean writeToFile = Boolean.parseBoolean(reader.readLine());
			if (writeToFile) {
				File file = new File("Engine2D" + File.separator + "log.log");

				if (!file.exists()) {
					f.createNewFile();
				}
				stream = new PrintStream(file);
				System.setOut(stream);
			}
			reader.close();
		} catch (Exception e) {
		}
	}

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

	/**
	 * Used when the program is terminated, will stop the logger logging
	 */
	public static void close() {
		if (stream != null) {
			stream.close();
		}
	}

}
