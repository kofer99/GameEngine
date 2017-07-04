/**
 * 
 */
package game;

import org.lwjgl.glfw.GLFW;

import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;

import gameengine.components.PhysicComponent;

/**
 * @author Daniel
 *
 */
public class Player extends ActionComponent {

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

	public Player(PhysicComponent ph) {
		playerPh = ph;

	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (i % 60 == 0)
			System.out.println(playerPh.getTransform().getPosition().toString());
			checkBoundaries(mxr, myr, playerPh);
			updateMovement();
		i++;
	}

	/**
	 * 
	 */
	private void updateMovement() {
		// TODO Auto-generated method stub
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_RIGHT) && !hasXMovement) {

			playerPh.setVelocity(new Vec3f(1f, 0f, 0.0f));
			hasXMovement = true;
			if (playerPh.getTransform().getPosition().x >= mxr) {
				playerPh.setVelocity(new Vec3f(0f, 0f, 0f));

			}

		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_LEFT) && !hasX2Movement) {
			playerPh.setVelocity(new Vec3f(-1f, 0f, 0.0f));
			hasX2Movement = true;
			if (playerPh.getTransform().getPosition().x <= -mxr) {
				playerPh.setVelocity(new Vec3f(0f, 0f, 0f));

			}
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_UP) && !hasYMovement) {
			playerPh.setVelocity(new Vec3f(0f, 1f, 0.0f));
			hasYMovement = true;
			if (playerPh.getTransform().getPosition().y >= myr) {
				playerPh.setVelocity(new Vec3f(0f, 0f, 0f));

			}
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_DOWN) && !hasY2Movement) {
			playerPh.setVelocity(new Vec3f(0f, -1f, 0.0f));
			hasY2Movement = true;
			if (playerPh.getTransform().getPosition().y <= -myr) {
				playerPh.setVelocity(new Vec3f(0f, 0f, 0f));

			}
		}

		if (Engine.keyboard.isUp(GLFW.GLFW_KEY_DOWN) && hasY2Movement) {
			playerPh.setVelocity(new Vec3f(0f, 0f, 0.0f));
			hasY2Movement = false;

		}
		if (Engine.keyboard.isUp(GLFW.GLFW_KEY_UP) && hasYMovement) {
			playerPh.setVelocity(new Vec3f(0f, 0f, 0.0f));
			hasYMovement = false;

		}
		if (Engine.keyboard.isUp(GLFW.GLFW_KEY_RIGHT) && hasXMovement) {
			playerPh.setVelocity(new Vec3f(0f, 0f, 0.0f));
			hasXMovement = false;

		}
		if (Engine.keyboard.isUp(GLFW.GLFW_KEY_LEFT) && hasX2Movement) {
			playerPh.setVelocity(new Vec3f(0f, 0f, 0.0f));
			hasX2Movement = false;
		}

	}

}
