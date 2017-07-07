/**
 * 
 */
package gameengine.components;

/**
 * @author Daniel
 *
 */
public class Quad extends Renderable {

	Transform transform;

	public Quad(String texture, Transform transform) {
		super(texture, transform);

	}

	public void setTransform(Transform transform) {
		// this.setposition(transform);
	}
}
