/**
 * 
 */
package gameengine;

/**
 * @author Florian Albrecht
 *
 */
public class Main {

	public static Engine engine;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		engine = new Engine();
		engine.start();
	}

}
