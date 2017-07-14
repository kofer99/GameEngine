/**
 * 
 */
package gameengine.systems.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * @author Daniel & Florian Albrecht
 *
 */
public class Mesh {

	private int EBO;
	private int VAO;
	private int VBO;

	public Mesh(float[] vertices, int[] indices) {

		EBO = GL15.glGenBuffers();
		VAO = GL30.glGenVertexArrays();
		VBO = GL15.glGenBuffers();

		// 1. bind Vertex Array Object
		glBindVertexArray(VAO);
		// 2. copy our vertices array in a vertex buffer for OpenGL to use
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		// 3. copy our index array in a element buffer for OpenGL to use
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		// 4. then set the vertex attributes pointers
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 20, 0);
		glEnableVertexAttribArray(0);
		// 5. Set Texture Pointers
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 12);
		glEnableVertexAttribArray(1);
	}

	public void bind() {
		glBindVertexArray(VAO);
	}

	public void unbind() {
		glBindVertexArray(0);
	}

	public void destroy() {
		unbind();
		glDeleteVertexArrays(VAO);
	}

	// hard coded Meshes

	public static Mesh Quad() {
		float vertices[] = {
				// x,y,z
				0.5f, 0.5f, 0.0f, 1.0f, 0.0f, // top right
				0.5f, -0.5f, 0.0f, 1.0f, 1.0f, // bottom right
				-0.5f, -0.5f, 0.0f, 0.0f, 1.0f, // bottom left
				-0.5f, 0.5f, 0.0f, 0.0f, 0.0f // top left
		};

		int indices[] = { // note that we start from 0!
				0, 1, 3, // first triangle
				1, 2, 3 // second triangle
		};

		Mesh quad = new Mesh(vertices, indices);

		return quad;
	}

}
