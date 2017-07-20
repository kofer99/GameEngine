package gameengine.util;

import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Transform;

public class StandardCollisionResponse implements ICollisionListener {

	public boolean cancelRotation = true;

	ActionComponent self;

	public StandardCollisionResponse(ActionComponent self) {
		this.self = self;
	}

	@Override
	public void onCollision(PhysicComponent otherPh) {
		PhysicComponent ownPh = self.getPhysicComponent();

		// HACK: Cancel any rotation when we're already colliding
		if (cancelRotation)
			ownPh.setRotVel(0);

		Transform ownTransform = ownPh.getTransform();
		Transform otherTransform = otherPh.getTransform();
		Vec3f otherVel = otherPh.getVelocity();
		Vec3f vel1 = ownPh.getVelocity();

		// Collision Response
		// Check for Movement in the same direction
		// check if Distance Increases or not
		if (Math.abs(ownTransform.getPosition().x - otherTransform.getPosition().x)
			- Math.abs(ownTransform.getPosition().x + vel1.x
			- (otherTransform.getPosition().x + otherVel.x)) >= 0) {

			double absPX = Math.abs(vel1.x);
			double absTX = Math.abs(otherVel.x);

			if (vel1.x == -otherVel.x) {
				vel1.x = 0;
			} else if (absPX < absTX) {
				vel1.x = otherVel.x;
			} else if(absPX > absTX) {
				// Other takes care
			}
		}

		if (Math.abs(ownTransform.getPosition().y - otherTransform.getPosition().y)
			- Math.abs(ownTransform.getPosition().y + vel1.y
			- (otherTransform.getPosition().y + otherVel.y)) >= 0) {

			double absPY = Math.abs(vel1.y);
			double absTY = Math.abs(otherVel.y);

			if (ownPh.getVelocity().y == -otherVel.y) {
				vel1.y = 0;
			} else if (absPY < absTY) {
				vel1.y = otherVel.y;
			} else if(absPY > absTY) {
				// Other takes care
			}
		}

		int edge = CollisionUtils.getCollidingEdge(ownPh);
		if (edge != CollisionUtils.NO_COLLISION) {
			vel1 = CollisionUtils.getVelocityFor(edge, ownPh);
		} else {
			int otherEdge = CollisionUtils.getCollidingEdge(otherPh);

			if (vel1.x > 0 && otherEdge == CollisionUtils.EDGE_RIGHT) {
				vel1.x = 0;
			}

			if (vel1.x < 0 && otherEdge == CollisionUtils.EDGE_LEFT) {
				vel1.x = 0;
			}

			if (vel1.y > 0 && otherEdge == CollisionUtils.EDGE_TOP) {
				vel1.y = 0;
			}

			if (vel1.y < 0 && otherEdge == CollisionUtils.EDGE_BOTTOM) {
				vel1.y = 0;
			}

			if ((vel1.y > 0 || vel1.x < 0) && otherEdge == CollisionUtils.EDGE_TOP_LEFT) {
				vel1.y = 0;
				vel1.x = 0;
			}

			if ((vel1.y > 0 || vel1.x > 0) && otherEdge == CollisionUtils.EDGE_TOP_RIGHT) {
				vel1.y = 0;
				vel1.x = 0;
			}

			if ((vel1.y < 0 || vel1.x < 0) && otherEdge == CollisionUtils.EDGE_BOTTOM_LEFT) {
				vel1.y = 0;
				vel1.x = 0;
			}

			if ((vel1.y < 0 || vel1.x > 0) && otherEdge == CollisionUtils.EDGE_BOTTOM_RIGHT) {
				vel1.y = 0;
				vel1.x = 0;
			}
		}

		ownPh.setVelocity(vel1);
	}
}
