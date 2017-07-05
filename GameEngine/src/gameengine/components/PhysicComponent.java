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
	private int collidable;

	/**
	 * @param type
	 */
	public PhysicComponent(Transform transform, int collidable) {
		super(ComponentType.PHYSIC);
		this.collidable = collidable;
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

	/**
	 * @param vec3f
	 */
	public void setVelocity(Vec3f nvelocity) {
		this.velocity = nvelocity;
	}

	/**
	 * @return the collidable
	 */
	public int isCollidable() {
		return collidable;
	}

	/**
	 * @param collidable the collidable to set
	 */
	public void setCollidable(int collidable) {
		this.collidable = collidable;
	}

}
