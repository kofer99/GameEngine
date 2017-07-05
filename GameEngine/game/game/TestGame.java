/**
 * 
 */
package game;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.components.actions.Player;
import gameengine.components.actions.TestAction;
import gameengine.objects.Entity;
import gameengine.objects.Game;

/**
 * @author Florian Albrecht
 *
 */
public class TestGame implements Game {

	public TestGame() {

	}

	@Override
	public void init() {
		Entity player = new Entity();
		Transform pT = new Transform(new Vec3f(0f, 0f, 0f), new Vec2f(1.0f, 1.0f));
		player.add(pT);
		player.add(new Renderable("Player.png", pT));
		PhysicComponent ph = new PhysicComponent(pT);
		player.add(ph);
		ActionComponent a = new Player(ph);
		player.add(a);

		Entity enemy1 = new Entity();
		Transform t1 = new Transform(new Vec3f(0f, 5f, 0f), new Vec2f(1.0f, 1.0f));
		enemy1.add(t1);
		enemy1.add(new Renderable("Enemy.png", t1));
		PhysicComponent ph2 = new PhysicComponent(t1);
		ph2.setVelocity(new Vec3f(0.25f, -0.25f, 0));
		enemy1.add(ph2);
	}

}
