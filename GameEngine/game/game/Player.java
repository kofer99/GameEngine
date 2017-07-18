package game;

import org.lwjgl.glfw.GLFW;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Transform;
import gameengine.util.CollisionUtils;

/**
 * @author Daniel
 * @author Amir
 *
 */
public class Player extends ActionComponent {

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameengine.components.ActionComponent#action(int)
	 */

	AudioComponent audio;
	PhysicComponent playerPh;
	boolean hasYMovement = false;
	boolean hasXMovement = false;
	boolean hasX2Movement = false;
	boolean hasY2Movement = false;

	public float mxr = 15.5f;
	public float myr = 8.5f;

	int i = 0;
	int z;

	public Player(PhysicComponent ph) {
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

	public void addAudio(AudioComponent a) {
		audio = a;
	}

	@Override
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
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_Q)) {
			rotvel = 1;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_E)) {
			rotvel = -1;
		}
		playerPh.setRotVel(rotvel);
	}

	private void updateMovement() {
		float xmov = 0.0f;
		float ymov = 0.0f;
		float currentrot = playerPh.getTransform().getRot().z;
		if (z > 60) {
			z = 0;
			audio.play(0.5f);
		}

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_RIGHT)) {
			xmov += 2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_LEFT)) {
			xmov += -2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_UP)) {
			ymov += +2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_DOWN)) {
			ymov += -2.0f;
		}
		if (xmov == 0 && ymov == 0) {
			playerPh.setVelocity(new Vec3f(0f, 0f, 0f));
			return;
		}

		z++;
		playerPh.setVelocity(Vec3f.normalize(new Vec3f(xmov, ymov, 0)));

	}
}