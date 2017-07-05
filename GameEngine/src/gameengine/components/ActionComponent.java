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
	public void checkBoundaries(float mxr,float myr,PhysicComponent ph){
		Vec3f mov = new Vec3f();
		if (ph.getTransform().getPosition().x >= mxr && ph.getVelocity().x > 0) {
			mov.y = ph.getVelocity().y;
			mov.x = 0;
			mov.z = 0;
			ph.setVelocity(mov);
			return;
		}
		if (ph.getTransform().getPosition().x <= -mxr && ph.getVelocity().x < 0) {
			mov.y = ph.getVelocity().y;
			mov.x = 0;
			mov.z = 0;
			ph.setVelocity(mov);
			return;
		}
		if (ph.getTransform().getPosition().y >= myr && ph.getVelocity().y > 0) {
			mov.x = ph.getVelocity().x;
			mov.y = 0;
			mov.z = 0;
			ph.setVelocity(mov);
			return;
		}
		if (ph.getTransform().getPosition().y <= -myr && ph.getVelocity().y < 0) {
			mov.x = ph.getVelocity().x;
			mov.y = 0;
			mov.z = 0;
			ph.setVelocity(mov);
			return;
		}
	}

}
