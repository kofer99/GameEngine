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
public class Transform extends Component {

	private Vec3f position;

	public Transform(Vec3f position) {
		super(ComponentType.TRANSFORM);
		this.position = position;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void add(Vec3f vec) {
		position.add(vec);
	}

}
