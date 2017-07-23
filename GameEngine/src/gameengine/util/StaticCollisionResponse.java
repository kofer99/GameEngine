/**
 * 
 */
package gameengine.util;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.PhysicComponent;

/**
 * @author Florian Albrecht
 *
 */
public class StaticCollisionResponse implements ICollisionListener {

	private ActionComponent action;

	public StaticCollisionResponse(ActionComponent action) {
		this.action = action;
	}

	@Override
	public void onCollision(PhysicComponent other, Vec2f mvt) {
		if (!other.CollisionTypes.contains(CollisionUtils.STATIC))
			return;

		PhysicComponent p = action.getPhysicComponent();
		// System.out.println("mvt = " + mvt);

		if (p.getRotVel() != 0)
			p.setRotVel(0);

		p.setVelocity(Vec3f.add(p.getVelocity(), new Vec3f(mvt, 0)));

	}

}
