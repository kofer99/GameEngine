/**
 * 
 */
package gameengine.systems;

import java.util.ArrayList;

import far.math.vec.Vec3f;
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
	private ComponentList<PhysicComponent> collidables;
	private ComponentList<PhysicComponent> phy;
	
	public Physics() {
		phy = new ComponentList<PhysicComponent>(ComponentType.PHYSIC);
		super.addList(phy);
	
		transforms = new ComponentList<Transform>(ComponentType.TRANSFORM);
		
		super.addList(transforms);
		collidables = new ComponentList<PhysicComponent>(ComponentType.Coll);
	}
	
	
	public void inititialize(EntityHandler entities) {
		entities.addComponents(transforms);
		

		
		System.out.println("transforms " + transforms.size());
		System.out.println("phy " + phy.size());
		
	}
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		for(PhysicComponent p : phy){
			if(p.isCollidable() == true){
				collidables.add(p);
				System.out.println("Coll" + collidables.size());
			}
		}
		
	}



	@Override
	public void update() {
		for (PhysicComponent p : phy) {
		//	System.out.println("test " + p.getVelocity().toString());
			
			p.getTransform().add(Vec3f.div(p.getVelocity(),1000));
			}
		
		}
	

	/* (non-Javadoc)
	 * @see gameengine.objects.EngineSystem#init()
	 */

}
