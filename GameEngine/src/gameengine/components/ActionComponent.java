/**
 * 
 */
package gameengine.components;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.util.CollisionUtils;

/**
 * @author Team
 *
 */
public abstract class ActionComponent extends Component {

	protected ActionComponent() {
		super(ComponentType.ACTION);
	}

	public abstract void action();

	public abstract PhysicComponent getPhysicComponent();

	protected void checkBoundaries(PhysicComponent ph) {
		ph.setVelocity(CollisionUtils.getVelocityFor(CollisionUtils.getCollidingEdge(ph), ph));
	}
}
