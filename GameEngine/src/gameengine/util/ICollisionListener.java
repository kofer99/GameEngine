package gameengine.util;

import far.math.vec.Vec2f;
import gameengine.components.PhysicComponent;

public interface ICollisionListener {
	public void onCollision(PhysicComponent other, Vec2f mvt);
}
