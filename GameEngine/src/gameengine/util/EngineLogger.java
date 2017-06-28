/**
 * 
 */
package gameengine.util;

/**
 * @author Florian Albrecht
 *
 */
public class EngineLogger {

	public static final int LOGALL = 0;

	public static final int INFO = 5;

	public static final int DEBUG = 100;

	private static int logLevel = 0;
	private static boolean specificLog = false;

	/**
	 * 
	 */
	public EngineLogger(int level) {
		logLevel = level;
	}

	public static void setLogLevel(int level) {
		logLevel = level;
	}

	public static void onlyLog(int level) {

	}

	public static void log(String message) {
		System.out.println(message);
	}

	public static void log(String message, int level) {
		if (logLevel <= level && !specificLog)
			System.out.println(message);
		else if (specificLog && logLevel == level)
			System.out.println(message);
	}

}
