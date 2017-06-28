/**
 * 
 */
package game;

import far.math.vec.Vec3f;
import gameengine.components.Renderable;
import gameengine.components.Transform;
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
		Entity e = new Entity();
		e.add(new Transform(new Vec3f(0f, 0f, 0f)));
		e.add(new Renderable("TEST"));

		Entity e1 = new Entity();
		e1.add(new Transform(new Vec3f(0f, 0f, 0f)));
		e1.add(new Renderable("TEST"));
		
		Entity e2 = new Entity();
		e2.add(new Transform(new Vec3f(0f, 0f, 0f)));
		
		Entity e3 = new Entity();
	}

}
