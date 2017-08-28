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
public class GuiShader extends Shader {

	private int projmatloc;
	private int rotmatloc;
	private int movmatloc;
	private int colorloc;
	private int cameraloc;

	public GuiShader() {
		super("gui");

		projmatloc = glGetUniformLocation(shaderProgram, "projmat");
		movmatloc = glGetUniformLocation(shaderProgram, "movmat");
		rotmatloc = glGetUniformLocation(shaderProgram, "rotmat");
		colorloc = glGetUniformLocation(shaderProgram, "color");
		cameraloc = glGetUniformLocation(shaderProgram, "camera");

		glUniformMatrix4fv(projmatloc, false, Mat4.createOrtho(-Game.camera.vRes / 4f, Game.camera.vRes / 4f,
				-Game.camera.hRes / 4f, Game.camera.hRes / 4f, 0.1f, 10.0f).getValue());
	}

	public void updateShader(Transform transform, Vec3f color) {
		glUniformMatrix4fv(movmatloc, false, Mat4
				.createTransformScaleMatrix(transform.getPosition(), new Vec3f(transform.getScale(), 1)).getValue());
		glUniformMatrix4fv(rotmatloc, false, Mat4.createRotationXYZMatrix(transform.getRot()).getValue());

		GL20.glUniform3f(colorloc, color.x, color.y, color.z);
		GL20.glUniform3f(cameraloc, -Game.camera.guix, -Game.camera.guiy, 0);
	}
}
