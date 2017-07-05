/**
 * 
 */
package gameengine.components.actions;

import org.lwjgl.glfw.GLFW;

import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;

import gameengine.components.PhysicComponent;

/**
 * @author Daniel
 * @author Amir
 *
 */
public class Player extends ActionComponent {

	private PhysicComponent playerPh;
	private boolean hasYMovement = false;
	private boolean hasXMovement = false;
	private boolean hasX2Movement = false;
	private boolean hasY2Movement = false;

	private float mxr = 15.5f;
	private float myr = 8.5f;
	private int i = 0;

	public Player(PhysicComponent ph) {
		playerPh = ph;

	}

	@Override
	public void action() {
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
		float xmov = 0.0f;
		float ymov = 0.0f;

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_RIGHT)) {
			xmov += 1.0f;
			if (playerPh.getTransform().getPosition().x >= mxr) {
				xmov = 0;
			}
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_LEFT)) {
			xmov += -1.0f;
			if (playerPh.getTransform().getPosition().x <= -mxr) {
				xmov = 0;
			}
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_UP)) {
			ymov += 1.0f;
			if (playerPh.getTransform().getPosition().y >= myr) {
				ymov = 0;
			}
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_DOWN)) {
			ymov += -1.0f;
			if (playerPh.getTransform().getPosition().y <= -myr) {
				ymov = 0;
			}
		}
		if (xmov == 0 && ymov == 0) {
			playerPh.setVelocity(new Vec3f(0f, 0f, 0f));
			return;
		}

		playerPh.setVelocity(Vec3f.normalize(new Vec3f(xmov, ymov, 0)));
	}

}
