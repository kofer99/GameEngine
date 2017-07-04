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
public abstract class ActionComponent extends Component {

	/**
	 * @param type
	 */
	protected ActionComponent() {
		super(ComponentType.ACTION);
	}

	public abstract void action();

}
