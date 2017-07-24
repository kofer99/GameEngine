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

		if (physics.getRotVel() != 0)
			physics.setRotVel(0);

		physics.setVelocity(Vec3f.add(physics.getVelocity(), new Vec3f(mvt, 0)));
	}
}
