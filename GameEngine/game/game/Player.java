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
	boolean hasYMovement = false;
	boolean hasXMovement = false;
	boolean hasX2Movement = false;
	boolean hasY2Movement = false;

	public float mxr = 15.5f;
	public float myr = 8.5f;

	int i = 0;
	int z;

	public Player(Entity player) {
		this.player = player;
	}

	@Override
	public void action() {
		updateMovement();
		updateRotation();
		checkBoundaries(player.pysics);

		i++;
	}

	@Override
	public PhysicComponent getPhysicComponent() {
		return player.pysics;
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
		float ymov = 0.0f;

		if (z > 60) {
			z = 0;
			player.audio.play(0.5f);
		}

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_RIGHT)) {
			xmov += 2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_LEFT)) {
			xmov += -2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_UP)) {
			ymov += +2.0f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_DOWN)) {
			ymov += -2.0f;
		}
		if (xmov == 0 && ymov == 0) {
			player.pysics.setVelocity(new Vec3f(0f, 0f, 0f));
			return;
		}

		z++;
		player.pysics.setVelocity(Vec3f.normalize(new Vec3f(xmov, ymov, 0)));
	}

	@Override
	public void onCollision(PhysicComponent other, Vec2f mvt) {
	}
}