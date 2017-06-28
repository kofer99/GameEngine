/**
 * 
 */
package gameengine.components;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;

/**
 * @author Florian Albrecht
 *
 */
public class Renderable extends Component {

	/**
	 * @param type
	 */
	public Renderable(String texture) {
		super(ComponentType.RENDERABLE);
	}

}
