package gameengine.util;

import gameengine.components.PhysicComponent;

public interface IUpdateListener {
	public void update(PhysicComponent self);

	// Runs at the end of the update method of Physics.java
	public void afterUpdate(PhysicComponent self);
}
