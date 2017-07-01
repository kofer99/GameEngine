/**
 * 
 */
package gameengine.components.actions;

import gameengine.components.ActionComponent;
import gameengine.components.Transform;

/**
 * @author Florian Albrecht
 *
 */
public class TestAction extends ActionComponent {

	private Transform t;

	/**
	 * @param type
	 */
	public TestAction(Transform t) {
		this.t = t;
	}

	@Override
	public void action(int key) {
		System.out.println("Action test! XD " + key);
	}

}
