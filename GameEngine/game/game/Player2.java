/**
 * 
 */
package game;

import org.lwjgl.glfw.GLFW;

import far.math.mat.Mat4;
import far.math.vec.Vec;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;

import gameengine.components.PhysicComponent;
import math.Vector4f;
import math.Vectorf;

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
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_R)) {

			rotvel = 1;

		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_T)) {

			rotvel = -1;
		}
		playerPh.setRotVel(rotvel);
	}

	/**
	 * 
	 */
	private void updateMovement() {
		// TODO Auto-generated method stub
        float xmov = 0.0f;
        float ymov = 0.0f;
        float currentrot = playerPh.getTransform().getRot().z;

        if (Engine.keyboard.isDown(GLFW.GLFW_KEY_D)) {
            xmov += 1.0f;

        }
        if (Engine.keyboard.isDown(GLFW.GLFW_KEY_A)) {
            xmov += -1.0f;

        }
        if (Engine.keyboard.isDown(GLFW.GLFW_KEY_W)) {
        	  ymov += +1.0f;

        }
        if (Engine.keyboard.isDown(GLFW.GLFW_KEY_S)) {
            ymov += -1.0f;

        }
        if(xmov==0 && ymov ==0 ){
        	playerPh.setVelocity(new Vec3f(0f,0f,0f));
        	return;
        	}
        playerPh.setVelocity(Vec3f.normalize(new Vec3f(xmov,ymov,0)));

	}

}
