/**
 * 
 */
package gameengine.components;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
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

import far.math.mat.Mat4;
import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.systems.Shader;
/**
 * @author Florian Albrecht
 *
 */
public class Renderable extends Component {
	 int VBO;
	 int EBO;
	 int VAO;
	// int shaderProgram;
	/**
	 * @param type
	 */
	public Renderable(String texture, Transform transform) {
		super(ComponentType.RENDERABLE);
	//	shaderProgram = glCreateProgram();
		
		float vertices[] = setPosition(transform);
		 int indices[] = {  // note that we start from 0!
			    0, 1, 3,   // first triangle
			    1, 2, 3    // second triangle
			}; 
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
		 glVertexAttribPointer(0, 3, GL_FLOAT, false,0,0);
		 glEnableVertexAttribArray(0);

	}
	public void render(){
		GL20.glUseProgram(Shader.shaderProgram);
		glBindVertexArray(VAO);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);
	}
	
	public float[] setPosition(Transform transform){
		Vec3f position = transform.getPosition();
		
		float vertices[] = {
				//x,y,z
				
			      +0.5f,  +0.5f,  +0.0f,  // top right
			      +0.5f,  -0.5f,  +0.0f,  // bottom right
			      -0.5f, -0.5f,  +0.0f,  // bottom left
			      -0.5f,  +0.5f,  +0.0f   // top left 
			};
		
		
		return vertices;
		
	}

}
