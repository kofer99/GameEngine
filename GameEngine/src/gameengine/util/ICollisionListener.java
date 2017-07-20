package gameengine.util;

import gameengine.components.PhysicComponent;

public interface ICollisionListener {
	public void onCollision(PhysicComponent other);
}
