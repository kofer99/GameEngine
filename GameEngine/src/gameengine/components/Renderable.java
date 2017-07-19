/**
 * 
 */
package gameengine.components;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.systems.graphics.Mesh;
import gameengine.systems.graphics.Shader;
import gameengine.systems.graphics.Texture;

/**
 * @author Daniel
 *
 */
public class Renderable extends Component {

	private Transform transform;
	private Texture texture;
	private Mesh mesh;

	/**
	 * @param type
	 */
	public Renderable(String texture, Transform transform) {
		super(ComponentType.RENDERABLE);
		this.transform = transform;
		this.texture = new Texture(texture);

		mesh = Mesh.Quad();

	}

	public void render() {
		mesh.bind();
		texture.bind();
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		mesh.unbind();
	}

	public void destroy() {
		mesh.unbind();
		mesh.destroy();
		texture.destroy();
	}

	public Transform getTransform() {
		return transform;
	}

}
