/**
 * 
 */
package gameengine.components;

import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;

/**
 * @author Florian Albrecht
 *
 */
public class PhysicComponent extends Component {

	private Transform transform;
	private Vec3f velocity;

	/**
	 * @param type
	 */
	public PhysicComponent(Transform transform) {
		super(ComponentType.PYSIC);
		this.transform = transform;
		velocity = new Vec3f();
	}

	/**
	 * @return the transform
	 */
	public Transform getTransform() {
		return transform;
	}

	/**
	 * @return the velocity
	 */
	public Vec3f getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void addVelocity(Vec3f nvelocity) {
		this.velocity.add(nvelocity);
	}

}
