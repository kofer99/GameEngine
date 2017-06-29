/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;
import gameengine.collections.EntityHandler;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
 *
 */
public class Physics extends EngineSystem {

	private ComponentList<Transform> transforms;
	private ComponentList<Renderable> renderable;

	public Physics() {
		transforms = new ComponentList<Transform>(ComponentType.TRANSFORM);
		renderable = new ComponentList<Renderable>(ComponentType.RENDERABLE);
		super.addList(transforms);
		super.addList(renderable);
	}

	public void init(EntityHandler entities) {
		entities.addComponents(transforms);
		entities.addComponents(renderable);
		System.out.println("transforms " + transforms.size()+"renderables"+renderable.size());
	}

	int i = 0;

	@Override
	public void update() {
		i++;
		if (i % 100 == 0) {
			String string = "" + transforms.size() + ": ";
			for (Transform t : transforms) {
				string += t.getEntityID() + t.getPosition().toString() + "/";
			}
			System.out.println("Transforms.size = " + string);

			return;
		}
		for(Renderable r: renderable){
			
			r.render();
		}
	}
}
