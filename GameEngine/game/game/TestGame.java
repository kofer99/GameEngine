/**
 * 
 */
package game;

import org.lwjgl.glfw.GLFW;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.Component;
import gameengine.objects.Entity;
import gameengine.objects.Game;
import gameengine.util.CollisionUtils;
import gameengine.util.StandardCollisionResponse;
import gameengine.util.StaticCollisionResponse;

/**
 *
 */
public class TestGame implements Game {

	private Transform playerTransform;
	private Renderable playerRender;

	public TestGame() {

	}

	@Override
	public void init() {
		Entity player = new Entity();
	
		playerTransform = new Transform(new Vec3f(7f, 0f, 0f), new Vec2f(2f, 2f), new Vec3f(0, 0, 270));
		PhysicComponent playerPhysic = new PhysicComponent(playerTransform);
		ActionComponent playerAction = new Player(player);
		AudioComponent audio = new AudioComponent("bounce.wav");
		
		playerRender = new Renderable("Player.png", playerTransform);
		
		playerPhysic.CollisionTypes.add(CollisionUtils.OTHER_PLAYER);
		playerPhysic.addCollisionListener(new StaticCollisionResponse(playerAction));
		playerPhysic.addCollisionListener(new StandardCollisionResponse(playerAction));
		playerPhysic.addCollisionListener(playerAction);

		player.add(playerTransform);
		player.add(playerRender);
		player.add(playerPhysic);
		player.add(playerAction);
		player.add(audio);

		Entity e = new Entity();

		Transform player2Transform = new Transform(new Vec3f(0f, 0.5f * i, 0f), new Vec2f(1.0f, 1.0f),
				new Vec3f(0, 0, 0));
		PhysicComponent player2Physics = new PhysicComponent(player2Transform);
		ActionComponent player2Action = new Player2(e);

		player2Physics.CollisionTypes.add(CollisionUtils.OTHER_PLAYER);
		player2Physics.addCollisionListener(new StaticCollisionResponse(player2Action));
		player2Physics.addCollisionListener(new StandardCollisionResponse(player2Action));
		player2Physics.addCollisionListener(player2Action);

		e.add(player2Transform);
		e.add(new Renderable("Grass.png", player2Transform));
		e.add(player2Physics);
		e.add(player2Action);

		for (int i = 0; i < 5; i++) {
			Entity g = new Entity();

			Transform gTransform = new Transform(new Vec3f(-15f + (i * 7.5f), -5f, 0f), new Vec2f(2.5f, 0.5f),
					new Vec3f(0, 0, 0));
			PhysicComponent gPhysics = new PhysicComponent(gTransform);

			gPhysics.CollisionTypes.add(CollisionUtils.STATIC);

			g.add(gTransform);
			g.add(new Renderable("Grass.png", gTransform));
			g.add(gPhysics);
		}

		Transform tt = new Transform(new Vec3f(-3.0f, 1.5f, 0.0f), new Vec2f(2.0f, 2.0f), new Vec3f(0, 0, 0));
		text = new Text("calibri", "Test Text gg xD !", tt, new Vec3f(0.5f, 1.0f, 0.5f));
	}

	Text text;
	int i = 0;
	int k = 0;
	@Override
	public void update() {
		i++;
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_K)) {
			camera.add(new Vec3f(0.0f, 0.1f, 0));
		}
		if (i == 180) {
			text.update("2. Text :)");
			text.updateColor(new Vec3f(0.2f, 1.0f, 0.0f));

			// System.out.println(camera.toString());
			// System.out.println(playerTransform.getPosition().toString());
			if(k%2==0){
				playerRender.updateTexture("Grass.png");
			}else{
				playerRender.updateTexture("Player.png");
			}
			k++;
			i = 0;
		}
	}
}
