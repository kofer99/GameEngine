
/**
 * 
 */
package game;

import org.lwjgl.glfw.GLFW;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;

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

	PhysicComponent playerPh;
	AudioComponent a;
	boolean hasYMovement = false;
	boolean hasXMovement = false;
	boolean hasX2Movement = false;
	boolean hasY2Movement = false;
	int z= 0;

	// Note: Those values are also hardcoded inside Physic.java's checkBoundaries method
	public float mxr = 15.5f;
	public float myr = 8.5f;

	int i = 0;

	public Player(PhysicComponent ph,AudioComponent a) {
		playerPh = ph;
		this.a =a;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (i % 60 == 0)
			System.out.println(playerPh.getTransform().getPosition().toString());

		updateMovement();
		updateRotation();
		checkBoundaries(mxr, myr, playerPh);
		i++;
	}

	/**
	 * 
	 */
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

	/**
	 * 
	 */
	private void updateMovement() {
		float xmov = 0.0f;
		float ymov = 0.0f;
		if(z >= 60){
			z = 0;
			a.play(0.5f);
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
		playerPh.setVelocity(Vec3f.normalize(new Vec3f(xmov,ymov,0)));
		if(playerPh.getVelocity().x !=0 || playerPh.getVelocity().y !=0)
				z++;
	}
}