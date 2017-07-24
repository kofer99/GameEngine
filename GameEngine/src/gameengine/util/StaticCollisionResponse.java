/**
 * 
 */
package gameengine.util;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.PhysicComponent;

/**
 * @author Florian Albrecht
 *
 */
public class StaticCollisionResponse implements ICollisionListener {

	@Override
	public void onCollision(PhysicComponent physics, PhysicComponent other, Vec2f mvt) {
		if (mvt == null)
			return;

		physics.setRotVel(0);
		physics.addVelocity(new Vec3f(mvt, 0));
	}
}
