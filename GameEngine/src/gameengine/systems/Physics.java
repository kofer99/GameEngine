/**
 * 
 */
package gameengine.systems;

import java.util.ArrayList;

import far.math.vec.Vec3f;
import gameengine.collections.ComponentList;
import gameengine.collections.EntityHandler;
import gameengine.components.PhysicComponent;
import gameengine.components.Transform;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Team
 *
 */
public class Physics extends EngineSystem {

	private ComponentList<Transform> transforms;
	private ArrayList<PhysicComponent> collidables;
	private ComponentList<PhysicComponent> phy;
	private int[] collidable = new int[100];

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
		for (PhysicComponent p : phy) {
			collidable[p.isCollidable()]++;
		}
	}

	@Override
	public void update() {
		for (PhysicComponent p : phy) {
			// System.out.println("test " + p.getVelocity().toString());
			p.getTransform().add(Vec3f.div(p.getVelocity(), 10));
			for (PhysicComponent t : phy) {
				if (p.isCollidable() == t.isCollidable() && !p.equals(t)) {
					checkcollision(p, t);
				}
			}
		}

	}

	/**
	 * @param p
	 * @param t
	 */
	private void checkcollision(PhysicComponent p, PhysicComponent t) {
		Vec3f distm = Vec3f.sub(p.getTransform().getPosition(), t.getTransform().getPosition());
	}

}
