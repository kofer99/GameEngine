/**
 * 
 */
package gameengine.components;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;

/**
 * @author Florian Albrecht, Daniel
 *
 */
public class Transform extends Component {

	private Vec3f position;
	private Vec2f scale;

	public Transform(Vec3f position, Vec2f scale) {
		super(ComponentType.TRANSFORM);
		this.position = position;
		this.scale = scale;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void add(Vec3f vec) {
		position.add(vec);
	}

	/**
	 * @return the scale
	 */
	public Vec2f getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(Vec2f scale) {
		this.scale = scale;
	}

}
