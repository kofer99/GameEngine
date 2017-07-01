/**
 * 
 */
package gameengine.components;

import org.lwjgl.glfw.GLFW;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;

/**
 * @author Florian Albrecht
 *
 */
public class InputComponent extends Component {

	private ActionComponent action;
	private int[] keys;

	/**
	 * @param type
	 */
	public InputComponent(ActionComponent action) {
		super(ComponentType.INPUT);
		this.action = action;
		keys = new int[] { GLFW.GLFW_KEY_SPACE };
	}

	public void action(int key) {
		action.action(key);
	}

	public int[] getKeys() {
		return keys;
	}

}
