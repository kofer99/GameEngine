/**
 * 
 */
package gameengine.components;

import far.math.vec.Vec3f;
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

	// This is probably crap as well
	public abstract void onCollision(ActionComponent other);
	public abstract PhysicComponent getPhysicComponent();

	protected void checkBoundaries(float mxr, float myr, PhysicComponent ph) {
		ph.setVelocity(getVelocityFor(getCollidingEdge(mxr, myr, ph), ph));
	}

	protected int getCollidingEdge(float mxr, float myr, PhysicComponent ph) {
		int type = CollisionUtils.NO_COLLISION;

		if (ph.getTransform().getPosition().x >= mxr && ph.getVelocity().x >= 0) {
			type = CollisionUtils.EDGE_RIGHT;
		} else if (ph.getTransform().getPosition().x <= -mxr && ph.getVelocity().x <= 0) {
			type = CollisionUtils.EDGE_LEFT;
		}

		if (ph.getTransform().getPosition().y >= myr && ph.getVelocity().y >= 0) {
			if (type == CollisionUtils.NO_COLLISION)
				return CollisionUtils.EDGE_TOP;

			return type == CollisionUtils.EDGE_RIGHT ? CollisionUtils.EDGE_TOP_RIGHT : CollisionUtils.EDGE_TOP_LEFT;
		} else if (ph.getTransform().getPosition().y <= -myr && ph.getVelocity().y <= 0) {
			if (type == CollisionUtils.NO_COLLISION)
				return CollisionUtils.EDGE_BOTTOM;

			return type == CollisionUtils.EDGE_RIGHT ? CollisionUtils.EDGE_BOTTOM_RIGHT : CollisionUtils.EDGE_BOTTOM_LEFT;
		}

		return type;
	}

	// Returns the velocity you should be using we hitting the map edge
	protected Vec3f getVelocityFor(int collisionType, PhysicComponent ph) {
		Vec3f mov = new Vec3f(ph.getVelocity());

		switch (collisionType) {
			case CollisionUtils.EDGE_RIGHT:
				mov.x = 0;
				break;
			case CollisionUtils.EDGE_LEFT:
				mov.x = 0;
				break;
			case CollisionUtils.EDGE_TOP:
				mov.y = 0;
				break;
			case CollisionUtils.EDGE_BOTTOM:
				mov.y = 0;
				break;
			case CollisionUtils.EDGE_TOP_LEFT:
				mov.x = 0;
				mov.y = 0;
				break;
			case CollisionUtils.EDGE_TOP_RIGHT:
				mov.x = 0;
				mov.y = 0;
				break;
			case CollisionUtils.EDGE_BOTTOM_LEFT:
				mov.x = 0;
				mov.y = 0;
				break;
			case CollisionUtils.EDGE_BOTTOM_RIGHT:
				mov.x = 0;
				mov.y = 0;
				break;
			case CollisionUtils.NO_COLLISION:
			default:
				// Nothing happens for now
		}

		return mov;
	}
}
