/**
 * 
 */
package gameengine.components;

import static org.lwjgl.opengl.GL11.glDrawArrays;

import org.lwjgl.opengl.GL11;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.systems.graphics.Mesh;
import gameengine.systems.graphics.Texture;

/**
 * @author Florian Albrecht
 *
 */
public class GUIRenderable extends Component {

	private Transform transform;
	private Texture texture;
	private Mesh mesh;

	public GUIRenderable(String texture, Transform transform, Mesh mesh) {
		super(ComponentType.GUIRENDERABLE);
		this.transform = transform;
		this.texture = new Texture(texture);
		this.mesh = mesh;
	}

	public void render() {
		texture.bind();

		mesh.bind();
		glDrawArrays(GL11.GL_QUADS, 0, mesh.getCount());
		mesh.unbind();
	}

	public void update(Mesh m) {
		mesh.unbind();
		mesh.destroy();
		mesh = m;
	}

	public Transform getTransform() {
		return transform;
	}

}
