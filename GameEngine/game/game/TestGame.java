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
<<<<<<< HEAD
	//	Entity e = new Entity();

=======
		Entity e = new Entity();
		Transform t = new Transform(new Vec3f(0f, 0f, 0f));
		e.add(t);
		e.add(new Renderable(t));
		PhysicComponent ph = new PhysicComponent(t);
		e.add(ph);
		ActionComponent a = new TestAction(ph);
		e.add(new InputComponent(a));

		Entity e1 = new Entity();
		Transform t1 = new Transform(new Vec3f(0f, 0f, 0f));
		e1.add(t1);
		e1.add(new Renderable(t1));

		Entity e2 = new Entity();
		e2.add(new Transform(new Vec3f(0f, 0f, 0f)));

>>>>>>> refs/remotes/albrecht-flo/master
		Entity e3 = new Entity();
		e3.add(new Renderable(null, new Transform(new Vec3f(1f,1f,0.6f), new Vec2f(1f,1f))));
	//	e3.add(new Transform(new Vec3f(0f, 0f, 0f)));
	}

}
