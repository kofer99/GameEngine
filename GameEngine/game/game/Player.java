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
        if(xmov==0 && ymov ==0 ){
        	playerPh.setVelocity(new Vec3f(0f,0f,0f));
        	return;
        	}
        playerPh.setVelocity(Vec3f.normalize(new Vec3f(xmov,ymov,0)));

	}

}
