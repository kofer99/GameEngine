/**
 * 
 */
package gameengine.util.exceptions;

/**
 * @author Florian Albrecht
 *
 */
public class IndexOutOfRange extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4707353179033264944L;

	/**
	 * 
	 */
	public IndexOutOfRange() {
	}

	/**
	 * 
	 * @param message
	 */
	public IndexOutOfRange(String arg0) {
		super(arg0);
	}
}
