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

	/* (non-Javadoc)
	 * @see gameengine.components.ActionComponent#action(int)
	 */

	PhysicComponent playerPh;
	public Player(PhysicComponent ph){
		playerPh = ph;

	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		if(Engine.keyboard.isDown(GLFW.GLFW_KEY_SPACE))
		playerPh.setVelocity(new Vec3f(-1f,1f,0.0f));
		
		if(Engine.keyboard.isUp(GLFW.GLFW_KEY_SPACE))
	    playerPh.setVelocity(new Vec3f(0f,0f,0.0f));
}

}
