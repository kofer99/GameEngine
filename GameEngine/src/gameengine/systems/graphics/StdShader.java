/**
 * 
 */
package gameengine.systems.graphics;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import org.lwjgl.opengl.GL20;

import far.math.mat.Mat4;
import far.math.vec.Vec3f;
import gameengine.components.Transform;
import gameengine.objects.Game;

/**
 * @author Daniel & Florian Albrecht
 *
 */
public class StdShader extends Shader {

	private int projmatloc;
	private int rotmatloc;
	private int movmatloc;
	private int cameraloc;
	private int scaleloc;

	public StdShader() {
		super("std");
	}

	// the get location calls may move into the constructor
	public void updateShader(Transform transform) {
		projmatloc = glGetUniformLocation(shaderProgram, "projmat");
		glUniformMatrix4fv(projmatloc, false,
				Mat4.createOrtho(-Game.camera.vRes, Game.camera.vRes, -Game.camera.hRes, Game.camera.hRes, 0.1f, 10.0f)
						.getValue());
		movmatloc = glGetUniformLocation(shaderProgram, "movmat");
		glUniformMatrix4fv(movmatloc, false, Mat4.createTransformMatrix(transform.getPosition()).getValue());
		rotmatloc = glGetUniformLocation(shaderProgram, "rotmat");
		glUniformMatrix4fv(rotmatloc, false, Mat4.createRotationXYZMatrix(transform.getRot()).getValue());
		int scaleloc = glGetUniformLocation(shaderProgram, "scalemat");
		GL20.glUniformMatrix4fv(scaleloc, false, Mat4.createScaleMatrix(new Vec3f(transform.getScale(), 1)).getValue());

		cameraloc = glGetUniformLocation(shaderProgram, "camera");
		GL20.glUniform3f(cameraloc, -Game.camera.x, -Game.camera.y, Game.camera.z);
	}
}
