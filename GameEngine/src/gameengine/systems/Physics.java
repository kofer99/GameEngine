/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;
<<<<<<< HEAD
import gameengine.collections.EntityHandler;
import gameengine.components.Renderable;
import gameengine.components.Transform;
=======
import gameengine.components.PhysicComponent;
>>>>>>> refs/remotes/albrecht-flo/master
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
 *
 */
public class Physics extends EngineSystem {

<<<<<<< HEAD
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
=======
	private ComponentList<PhysicComponent> phy;

	public Physics() {
		phy = new ComponentList<PhysicComponent>(ComponentType.PYSIC);
		super.addList(phy);
	}

	@Override
	protected void init() {
		System.out.println("phy " + phy.size());
>>>>>>> refs/remotes/albrecht-flo/master
	}

	@Override
	public void update() {
<<<<<<< HEAD
		
		for(Renderable r: renderable){
			
			r.render();
=======
		for (PhysicComponent p : phy) {
			//System.out.println("test " + p.getVelocity().toString());
>>>>>>> refs/remotes/albrecht-flo/master
		}
	}
}
