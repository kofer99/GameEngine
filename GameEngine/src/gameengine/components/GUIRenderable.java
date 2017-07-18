/**
 * 
 */
package gameengine.components;

import static org.lwjgl.opengl.GL11.glDrawArrays;

import org.lwjgl.opengl.GL11;

import far.math.vec.Vec3f;
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
	private Vec3f color;

	public GUIRenderable(String texture, Transform transform, Mesh mesh, Vec3f color) {
		super(ComponentType.GUIRENDERABLE);
		this.transform = transform;
		this.texture = new Texture(texture);
		this.mesh = mesh;
		this.color = color;
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

	public Vec3f getColor() {
		return color;
	}

	public void setColor(Vec3f ncolor) {
		color = ncolor;
	}

}
