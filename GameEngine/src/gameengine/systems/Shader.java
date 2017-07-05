/**
 * 
 */
package gameengine.systems;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import far.math.mat.Mat4;
import far.math.vec.Vec3f;
import gameengine.components.Transform;
import gameengine.util.IOStream;

/**
 * @author Daniel
 *
 */
public class Shader {
	static int  vertexShader ;
		static int fragmentShader;
		public static int shaderProgram;
		static int projmatloc;
		static int rotmatloc;
		public static int movmatloc;

	public static void createShader(){
		String vpath = IOStream.read("src/gameengine/systems/std.vert");
		String fpath = IOStream.read("src/gameengine/systems/std.frag");
		

		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader,vpath); 		
		glCompileShader(vertexShader);
		
		checkShaderErrors(vertexShader, GL_VERTEX_SHADER, "Vertexshader");
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fpath);
		glCompileShader(fragmentShader);
		checkShaderErrors(fragmentShader, GL_FRAGMENT_SHADER, "Fragmentshader");
		
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		checkProgramErrors(shaderProgram, GL20.GL_LINK_STATUS);

	}
	public static void updateShader(Transform transform){
		projmatloc=glGetUniformLocation(shaderProgram, "projmat");
		glUniformMatrix4fv(projmatloc,false,Mat4.createOrtho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f).getValue());
		movmatloc=glGetUniformLocation(shaderProgram, "movmat");
		glUniformMatrix4fv(movmatloc,false,Mat4.createTransformScaleMatrix(transform.getPosition(), new Vec3f(transform.getScale(),1)).getValue());
		rotmatloc = glGetUniformLocation(shaderProgram, "rotmat");
		glUniformMatrix4fv(rotmatloc, false, Mat4.createRotationXYZMatrix(transform.getRot()).getValue());
		
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
