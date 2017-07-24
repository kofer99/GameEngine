/**
 * 
 */
package gameengine.components;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.util.CollisionUtils;
import gameengine.util.ICollisionListener;

/**
 * @author Team
 *
 */
public abstract class ActionComponent extends Component implements ICollisionListener {

	protected ActionComponent() {
		super(ComponentType.ACTION);
	}

	public abstract void action();

}
