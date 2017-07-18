package game;

import org.lwjgl.glfw.GLFW;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Transform;
import gameengine.util.CollisionUtils;

/**
 * @author Daniel
 * @author Amir
 *
 */
public class Player2 extends ActionComponent {

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameengine.components.ActionComponent#action(int)
	 */

	PhysicComponent playerPh;
	boolean hasYMovement = false;
	boolean hasXMovement = false;
	boolean hasX2Movement = false;
	boolean hasY2Movement = false;

	float mxr = 15.5f;
	float myr = 8.5f;

	int i = 0;

	public Player2(PhysicComponent ph) {
		playerPh = ph;
		playerPh.ControllingPlayer = this;
	}

	@Override
	public void action() {
		updateMovement();
		updateRotation();
		checkBoundaries(mxr, myr, playerPh);

		i++;
	}

	public void onCollision(ActionComponent other) {
		// HACK: Cancel any rotation when we're already colliding
		playerPh.setRotVel(0);

		PhysicComponent otherPh = other.getPhysicComponent();
		Transform ownTransform = playerPh.getTransform();
		Transform otherTransform = otherPh.getTransform();
		Vec3f otherVel = otherPh.getVelocity();
		Vec3f vel1 = playerPh.getVelocity();

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

			if (playerPh.getVelocity().y == -otherVel.y) {
				vel1.y = 0;
			} else if (absPY < absTY) {
				vel1.y = otherVel.y;
			} else if(absPY > absTY) {
				// Other takes care
			}
		}

		int edge = getCollidingEdge(mxr, myr, playerPh);
		if (edge != CollisionUtils.NO_COLLISION) {
			vel1 = getVelocityFor(edge, playerPh);
		} else {
			int otherEdge = getCollidingEdge(mxr, myr, otherPh);

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

		playerPh.setVelocity(vel1);
	}

	@Override
	public PhysicComponent getPhysicComponent() {
		return playerPh;
	}

	private void updateRotation() {
		float rotvel = 0f;
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_R)) {

			rotvel = 1;

		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_T)) {

			rotvel = -1;
		}
		playerPh.setRotVel(rotvel);
	}

	private void updateMovement() {
		float xmov = 0.0f;
		float ymov = 0.0f;
		float currentrot = playerPh.getTransform().getRot().z;

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_D)) {
			xmov += 2.0f;

		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_A)) {
			xmov += -2.0f;

		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_W)) {
			System.out.println(currentrot);
			  ymov += +2.0f;

		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_S)) {
			ymov += -2.0f;

		}

		playerPh.setVelocity(Vec3f.normalize(new Vec3f(xmov,ymov,0)));
	}
}
