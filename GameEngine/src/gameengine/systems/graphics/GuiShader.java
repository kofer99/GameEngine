/**
 * 
 */
package gameengine.systems.graphics;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import far.math.mat.Mat4;
import far.math.vec.Vec3f;
import gameengine.components.Transform;

/**
 * @author Daniel & Florian Albrecht
 *
 */
public class GuiShader extends Shader {

	private int projmatloc;
	private int rotmatloc;
	private int movmatloc;

	public GuiShader() {
		super("gui");
	}

	// the get location calls may move into the constructor
	public void updateShader(Transform transform) {
		projmatloc = glGetUniformLocation(shaderProgram, "projmat");
		movmatloc = glGetUniformLocation(shaderProgram, "movmat");
		rotmatloc = glGetUniformLocation(shaderProgram, "rotmat");
		glUniformMatrix4fv(projmatloc, false,
				Mat4.createOrtho(-16.0f / 8f, 16.0f / 8f, -9.0f / 8f, 9.0f / 8f, -0.1f, 1.0f).getValue());
		glUniformMatrix4fv(movmatloc, false, Mat4
				.createTransformScaleMatrix(transform.getPosition(), new Vec3f(transform.getScale(), 1)).getValue());
		glUniformMatrix4fv(rotmatloc, false, Mat4.createRotationXYZMatrix(transform.getRot()).getValue());

	}
}
