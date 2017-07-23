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

	ActionComponent action;

	public StaticCollisionResponse(ActionComponent action) {
		this.action = action;
	}

	@Override
	public void onCollision(PhysicComponent other, Vec2f mvt) {
		PhysicComponent p = action.getPhysicComponent();
		// System.out.println("mvt = " + mvt);
		p.setVelocity(Vec3f.add(p.getVelocity(), new Vec3f(mvt, 0)));

	}

}
