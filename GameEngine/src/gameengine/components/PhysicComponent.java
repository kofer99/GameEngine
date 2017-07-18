/**
 * 
 */
package gameengine.components;

import java.util.HashSet;
import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.util.CollisionUtils;

/**
 * @author Florian Albrecht
 *
 */
public class PhysicComponent extends Component {

	private Transform transform;
	private Vec3f velocity;
	private float rotvel;

	public HashSet<Integer> CollisionTypes = new HashSet<Integer>();
	public ActionComponent ControllingPlayer;

	public PhysicComponent(Transform transform) {
		super(ComponentType.PHYSIC);
		this.transform = transform;
		velocity = new Vec3f();
		rotvel = 0;
	}

	// TODO: This is crap
	public PhysicComponent(Transform transform, boolean canCollide) {
		this(transform);

		if (!canCollide)
			CollisionTypes.add(CollisionUtils.NO_COLLISION);
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

	public float getRotVel() {
		return rotvel;
	}

	public void setRotVel(float rotvel) {
		this.rotvel = rotvel;
	}
}
