/**
 * 
 */
package gameengine.util;

import far.math.vec.Vec3f;
import gameengine.components.PhysicComponent;
import gameengine.objects.Game;

public class CollisionUtils {
	public static final int NO_COLLISION = 0;
	public static final int EDGE_LEFT = 1;
	public static final int EDGE_RIGHT = 2;
	public static final int EDGE_TOP = 3;
	public static final int EDGE_BOTTOM = 4;
	public static final int EDGE_TOP_LEFT = 5;
	public static final int EDGE_TOP_RIGHT = 6;
	public static final int EDGE_BOTTOM_LEFT = 7;
	public static final int EDGE_BOTTOM_RIGHT = 8;
	public static final int OTHER_PLAYER = 9;

	public static boolean CanCollide(PhysicComponent a, PhysicComponent b) {
		if (a.CollisionTypes.contains(NO_COLLISION) || b.CollisionTypes.contains(NO_COLLISION))
			return false;

		for (int type : a.CollisionTypes)
			if (b.CollisionTypes.contains(type))
				return true;

		return false;
	}

	public static int getCollidingEdge(PhysicComponent ph) {
		float mxr = ph.mxr;
		float myr = ph.myr;
		int type = CollisionUtils.NO_COLLISION;

		if (ph.getTransform().getPosition().x >=Game.camera.x*2+ mxr && ph.getVelocity().x >= 0) {
			type = CollisionUtils.EDGE_RIGHT;
		} else if (ph.getTransform().getPosition().x <=Game.camera.x*2 -mxr && ph.getVelocity().x <= 0) {
			type = CollisionUtils.EDGE_LEFT;
		}

		if (ph.getTransform().getPosition().y >=Game.camera.y*2+ myr && ph.getVelocity().y >= 0) {
			if (type == CollisionUtils.NO_COLLISION)
				return CollisionUtils.EDGE_TOP;

			return type == CollisionUtils.EDGE_RIGHT ? CollisionUtils.EDGE_TOP_RIGHT : CollisionUtils.EDGE_TOP_LEFT;
		} else if (ph.getTransform().getPosition().y <= Game.camera.y*2 -myr && ph.getVelocity().y <= 0) {
			if (type == CollisionUtils.NO_COLLISION)
				return CollisionUtils.EDGE_BOTTOM;

			return type == CollisionUtils.EDGE_RIGHT ? CollisionUtils.EDGE_BOTTOM_RIGHT : CollisionUtils.EDGE_BOTTOM_LEFT;
		}

		return type;
	}

	// Returns the velocity you should be using we hitting the map edge
	public static Vec3f getVelocityFor(int collisionType, PhysicComponent ph) {
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
