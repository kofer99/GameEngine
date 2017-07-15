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
import gameengine.objects.Entity;
import gameengine.objects.Game;

/**
 *
 */
public class TestGame implements Game {

	public TestGame() {

	}

	@Override
	public void init() {
		Entity player = new Entity();

		Transform playerTransform = new Transform(new Vec3f(13f, 5f, 0f), new Vec2f(2f, 2f), new Vec3f(0, 0, 0));
		PhysicComponent playerPhysic = new PhysicComponent(playerTransform, 1);
		ActionComponent playerAction = new Player(playerPhysic);

		player.add(playerTransform);
		player.add(new Renderable("Player.png", playerTransform));
		player.add(playerPhysic);
		player.add(playerAction);

		Entity e = new Entity();

		Transform player2Transform = new Transform(new Vec3f(5f, 5f, 0f), new Vec2f(2f, 2f), new Vec3f(0, 0, 0));
		PhysicComponent player2Physics = new PhysicComponent(player2Transform, 1);
		ActionComponent player2Action = new Player2(player2Physics);

		e.add(player2Transform);
		e.add(new Renderable("Enemy.png", player2Transform));
		e.add(player2Physics);
		e.add(player2Action);

		Transform tt = new Transform(new Vec3f(-2.0f, 0.8f, 0.0f), new Vec2f(1.0f, 1.0f), new Vec3f(0, 0, 0));
		Text text = new Text("arial", "Test Text gg xD !", tt);
	}

	@Override
	public void update() {

	}

}
