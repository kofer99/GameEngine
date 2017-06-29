/**
 * 
 */
package gameengine.systems;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Daniel
 *
 */
public class Shader {
	static int  vertexShader ;
		static int fragmentShader;
		public static 	int shaderProgram;
	public static void createShader(){
		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader,"std.vert"); 
		glCompileShader(vertexShader);
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, "std.frag");
		glCompileShader(fragmentShader);
		
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
	}
	
}
