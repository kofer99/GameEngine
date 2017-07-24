package gameengine.util;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.PhysicComponent;

public class StandardGravity implements IUpdateListener, ICollisionListener {

	public float force;

	boolean hasCollided;
	float timesToApply = 1;

	public StandardGravity(float force) {
		this.force = force;
	}

	@Override
	public void update(PhysicComponent self) {
		self.addVelocity(new Vec3f(0, (-force) * timesToApply, 0));
	}

	@Override
	public void afterUpdate(PhysicComponent self) {
		if (hasCollided)
			timesToApply = 0;
		else
			timesToApply++;

		hasCollided = false;
	}

	@Override
	public void onCollision(PhysicComponent self, PhysicComponent other, Vec2f mvt) {
		if (mvt != null) {
			if (mvt.x != 0)
				return;
			else
				hasCollided = true;
		}

		int edge = CollisionUtils.getCollidingEdge(self);
		if (edge == CollisionUtils.EDGE_LEFT || edge == CollisionUtils.EDGE_RIGHT)
			return;

		hasCollided = timesToApply > 0 || CollisionUtils.NO_COLLISION != CollisionUtils
				.getCollidingEdgeWithVelocity(self, new Vec3f(0, (-force) * timesToApply, 0));
	}
}
