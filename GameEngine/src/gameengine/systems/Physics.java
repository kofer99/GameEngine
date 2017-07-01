/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;
import gameengine.collections.EntityHandler;
import gameengine.components.Transform;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
 *
 */
public class Physics extends EngineSystem {

	private ComponentList<Transform> transforms;

	public Physics() {
		transforms = new ComponentList<Transform>(ComponentType.TRANSFORM);
		super.addList(transforms);
	}

	@Override
	protected void init() {
		System.out.println("transforms " + transforms.size());
	}

	@Override
	public void update() {

	}
}
