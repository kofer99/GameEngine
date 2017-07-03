/**
 * 
 */
package game;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.InputComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
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

	//	Entity e = new Entity();

		Entity e3 = new Entity();
		e3.add(new Renderable(null, new Transform(new Vec3f(1f,1f,0.6f), new Vec2f(1f,1f))));
	//	e3.add(new Transform(new Vec3f(0f, 0f, 0f)));
	}

}
