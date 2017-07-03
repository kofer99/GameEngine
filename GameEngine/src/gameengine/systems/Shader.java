/**
 * 
 */
package gameengine.systems;

import static org.lwjgl.opengl.GL20.*;

import far.math.mat.Mat4;
import gameengine.util.IOStream;

/**
 * @author Daniel
 *
 */
public class Shader {
	static int  vertexShader ;
		static int fragmentShader;
		public static 	int shaderProgram;
		static int projmatloc;

	public static void createShader(){
		String vpath = IOStream.read("src/gameengine/systems/std.vert");
		String fpath = IOStream.read("src/gameengine/systems/std.frag");
		
		
		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader,vpath); 
		
		
		glCompileShader(vertexShader);
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fpath);
		glCompileShader(fragmentShader);
		
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		
		projmatloc=glGetUniformLocation(shaderProgram, "projmat");
		glUniformMatrix4fv(projmatloc,false,Mat4.createOrtho(-16.0f, 16.0f, -9.0f, 9.0f, 0.5f, 1.0f).getValue());
	}
	
}
