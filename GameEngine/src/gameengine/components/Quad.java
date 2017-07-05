/**
 * 
 */
package gameengine.components;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;

/**
 * @author Daniel
 *
 */
public class Quad extends Renderable {

	private Transform transform;

	public Quad(String texture, Transform transform) {
		super(texture, transform);

	}

	public void setTransform(Transform transform) {
		// this.setposition(transform);
	}
}
