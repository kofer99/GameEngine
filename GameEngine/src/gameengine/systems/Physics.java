/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;

import gameengine.collections.EntityHandler;
import gameengine.components.Renderable;
import gameengine.components.Transform;

import gameengine.components.PhysicComponent;

import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
 *
 */
public class Physics extends EngineSystem {

	private ComponentList<Transform> transforms;


	public Physics() {
		phy = new ComponentList<PhysicComponent>(ComponentType.PHYSIC);
		super.addList(phy);
	
		transforms = new ComponentList<Transform>(ComponentType.TRANSFORM);
		
		super.addList(transforms);
		
	}

	public void inititialize(EntityHandler entities) {
		entities.addComponents(transforms);

		System.out.println("transforms " + transforms.size());
		System.out.println("phy " + phy.size());
	}
	private ComponentList<PhysicComponent> phy;



	@Override
	public void update() {
		for (PhysicComponent p : phy) {
			System.out.println("test " + p.getVelocity().toString());
			p.getTransform().add(p.getVelocity());
			}
		}
	

	/* (non-Javadoc)
	 * @see gameengine.objects.EngineSystem#init()
	 */
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
}
