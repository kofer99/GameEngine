/**
 * 
 */
package gameengine.systems;

import far.math.vec.Vec3f;
import gameengine.Main;
import gameengine.collections.ComponentList;
import gameengine.collections.EntityHandler;
import gameengine.components.Transform;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;
import gameengine.objects.Entity;

/**
 * @author Florian Albrecht
 *
 */
public class EngineActionController extends EngineSystem {

	private ComponentList<Transform> transforms;

	public EngineActionController() {
		transforms = new ComponentList<Transform>(ComponentType.TRANSFORM);
		super.addList(transforms);
	}

	@Override
	public void init() {
	}

	int i = 0;

	@Override
	public void update() {
		i++;
		if (i % 100 == 0) {
			Entity e = new Entity();
			e.add(new Transform(new Vec3f(i, 0f, 0f)));
		}
		if (i % 400 == 0)
			Main.engine.removeEntity(1);
	}

}
