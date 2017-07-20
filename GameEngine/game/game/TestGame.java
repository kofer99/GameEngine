/**
 * 
 */
package game;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.Entity;
import gameengine.objects.Game;
import gameengine.util.CollisionUtils;

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
		PhysicComponent playerPhysic = new PhysicComponent(playerTransform);
		ActionComponent playerAction = new Player(playerPhysic);
		AudioComponent audio = new AudioComponent("bounce.wav");
		((Player) playerAction).addAudio(audio);

		playerPhysic.CollisionTypes.add(CollisionUtils.OTHER_PLAYER);

		player.add(playerTransform);
		player.add(new Renderable("Player.png", playerTransform));
		player.add(playerPhysic);
		player.add(playerAction);
		player.add(audio);

		Entity e = new Entity();

		Transform player2Transform = new Transform(new Vec3f(5f, 5f, 0f), new Vec2f(2f, 2f), new Vec3f(0, 0, 0));
		PhysicComponent player2Physics = new PhysicComponent(player2Transform);
		ActionComponent player2Action = new Player2(player2Physics);

		player2Physics.CollisionTypes.add(CollisionUtils.OTHER_PLAYER);

		e.add(player2Transform);
		e.add(new Renderable("Enemy.png", player2Transform));
		e.add(player2Physics);
		e.add(player2Action);

		Transform tt = new Transform(new Vec3f(-3.0f, 1.5f, 0.0f), new Vec2f(2.0f, 2.0f), new Vec3f(0, 0, 0));
		text = new Text("calibri", "Test Text gg xD !", tt, new Vec3f(0.5f, 1.0f, 0.5f));
	}

	Text text;
	int i = 0;

	@Override
	public void update() {
		i++;

		if (i == 180) {
			text.update("2. Text :)");
			text.updateColor(new Vec3f(0.2f, 1.0f, 0.0f));
		}
	}
}
