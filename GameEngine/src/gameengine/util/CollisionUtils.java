/**
 * 
 */
package gameengine.util;

import gameengine.components.PhysicComponent;

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
}
