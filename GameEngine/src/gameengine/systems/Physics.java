/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;
import gameengine.components.PhysicComponent;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
 *
 */
public class Physics extends EngineSystem {

	private ComponentList<PhysicComponent> phy;

	public Physics() {
		phy = new ComponentList<PhysicComponent>(ComponentType.PYSIC);
		super.addList(phy);
	}

	@Override
	protected void init() {
		System.out.println("phy " + phy.size());
	}

	@Override
	public void update() {
		for (PhysicComponent p : phy) {
			//System.out.println("test " + p.getVelocity().toString());
		}
	}
}
