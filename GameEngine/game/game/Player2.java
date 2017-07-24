package game;

import org.lwjgl.glfw.GLFW;
import far.math.vec.Vec2f;
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

	private Entity player2;

	public Player2(Entity player2) {
		super(player2);
		this.player2 = player2;
	}

	@Override
	public void action() {
		move(2, GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D, GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_S);
		updateRotation();
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

	@Override
	public void onCollision(PhysicComponent self, PhysicComponent other, Vec2f mvt) {

	}
}
