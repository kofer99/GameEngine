package game;

import org.lwjgl.glfw.GLFW;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;
import gameengine.components.PhysicComponent;
import gameengine.objects.Entity;

/**
 * @author Daniel
 * @author Amir
 *
 */
public class Player2 extends ActionComponent {

	boolean hasYMovement = false;
	boolean hasXMovement = false;
	boolean hasX2Movement = false;
	boolean hasY2Movement = false;

	int i = 0;

	private Entity player2;

	public Player2(Entity player2) {
		this.player2 = player2;
	}

	@Override
	public void action() {
		updateMovement();
		updateRotation();
		checkBoundaries(player2.pysics);

		i++;
	}

	@Override
	public PhysicComponent getPhysicComponent() {
		return player2.pysics;
	}

	private void updateRotation() {
		float rotvel = 0f;

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_R)) {
			rotvel = 1;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_T)) {
			rotvel = -1;
		}

		player2.pysics.setRotVel(rotvel);
	}

	private void updateMovement() {
		float xmov = 0.0f;
		float ymov = 0.0f;

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_D)) {
			xmov += 2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_A)) {
			xmov += -2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_W)) {
			ymov += +2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_S)) {
			ymov += -2.0f;
		}

		player2.pysics.setVelocity(Vec3f.normalize(new Vec3f(xmov, ymov, 0)));
	}

	@Override
	public void onCollision(PhysicComponent other, Vec2f mvt) {
	}
}
