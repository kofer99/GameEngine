
/**
 * 
 */
package gameengine.systems.graphics;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import gameengine.util.IOStream;

/**
 * @author Daniel & Flo
 *
 */
public class Shader {

	protected int shaderProgram;

	protected Shader(String source) {
		this.shaderProgram = createShader(source);

	}

	private int createShader(String source) {
		String vpath = IOStream.read("res/shader/" + source + ".vert");
		String fpath = IOStream.read("res/shader/" + source + ".frag");

		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vpath);
		glCompileShader(vertexShader);

		checkShaderErrors(vertexShader, GL_VERTEX_SHADER, "Vertexshader");

		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fpath);
		glCompileShader(fragmentShader);
		checkShaderErrors(fragmentShader, GL_FRAGMENT_SHADER, "Fragmentshader");

		int shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		checkProgramErrors(shaderProgram, GL20.GL_LINK_STATUS);

		glUseProgram(shaderProgram);

		return shaderProgram;
	}

	public void bind() {
		GL20.glUseProgram(shaderProgram);
	}

	public void unbind() {
		GL20.glUseProgram(0);
	}

	public void destroy() {
		unbind();
		GL20.glDeleteProgram(shaderProgram);
	}

	private static boolean checkShaderErrors(int shader, int type, String name) {
		int shaderStatus = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		if (shaderStatus == GL11.GL_FALSE) {
			if (type == GL20.GL_VERTEX_SHADER)
				System.err.println(name + ": Error in VertexShader:");
			else
				System.err.println(name + ": Error in FragmentShader:");
			System.err.println(GL20.glGetShaderInfoLog(shader));
			return false;
		}
		return true;
	}

	private static boolean checkProgramErrors(int program, int status) {
		int programStatus = GL20.glGetProgrami(program, status);
		if (programStatus == GL11.GL_FALSE) {
			System.err.println(GL20.glGetProgramInfoLog(program) + "\n");
			return false;
		}
		return true;
	}

}