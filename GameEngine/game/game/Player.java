package game;

import org.lwjgl.glfw.GLFW;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.objects.Entity;

/**
 * @author Daniel
 * @author Amir
 *
 */
public class Player extends ActionComponent {

	private Entity player;

	public Player(Entity player) {
		this.player = player;
	}

	@Override
	public void action() {
		updateMovement();
		updateRotation();
	}

	private void updateRotation() {
		float rotvel = 0f;

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_Q)) {
			rotvel = 1;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_E)) {
			rotvel = -1;
		}

		player.pysics.setRotVel(rotvel);
	}

	private void updateMovement() {
		float xmov = 0.0f;

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_RIGHT)) {
			xmov += 2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_LEFT)) {
			xmov += -2.0f;
		}

		player.pysics.setVelocity(Vec3f.normalize(new Vec3f(xmov, 0, 0)));
	}

	@Override
	public void onCollision(PhysicComponent self, PhysicComponent other, Vec2f mvt) {

	}
}