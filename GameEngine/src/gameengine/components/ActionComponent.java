/**
 * 
 */
package gameengine.components;

import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.systems.Physics;

/**
 * @author Florian Albrecht
 *
 */
public abstract class ActionComponent extends Component {

	/**
	 * @param type
	 */
	protected ActionComponent() {
		super(ComponentType.ACTION);
	}

	public abstract void action();


	protected void checkBoundaries(float mxr, float myr, PhysicComponent ph) {
		if (ph.getTransform().getPosition().x >= mxr && ph.getVelocity().x > 0) {
			ph.setVelocity(new Vec3f(0f, 0f, 0f));
			return;
		}
		if (ph.getTransform().getPosition().x <= -mxr && ph.getVelocity().x < 0) {
			ph.setVelocity(new Vec3f(0f, 0f, 0f));
			return;
		}
		if (ph.getTransform().getPosition().y >= myr && ph.getVelocity().y > 0) {
			ph.setVelocity(new Vec3f(0f, 0f, 0f));
			return;
		}
		if (ph.getTransform().getPosition().y <= -myr && ph.getVelocity().y < 0) {
			ph.setVelocity(new Vec3f(0f, 0f, 0f));

			return;
		}
	}

}
