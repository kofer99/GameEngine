/**
 * 
 */
package gameengine.components.actions;

import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.PhysicComponent;

/**
 * @author Florian Albrecht
 *
 */
public class TestAction extends ActionComponent {

	private PhysicComponent ph;

	/**
	 * @param type
	 */
	public TestAction(PhysicComponent ph) {
		this.ph = ph;
	}

	@Override
	public void action(int key) {
		ph.setVelocity(new Vec3f(0.1f, 0, 0));
	}

}
