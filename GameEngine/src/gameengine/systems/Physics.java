/**
 * 
 */
package gameengine.systems;

import far.math.mat.Mat4;
import far.math.mat.Matrixf;
import far.math.vec.Vec;
import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.collections.ComponentList;
import gameengine.components.PhysicComponent;
import gameengine.components.Transform;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;
import gameengine.util.CollisionUtils;

/**
 * @author Team
 *
 */
public class Physics extends EngineSystem {

	private ComponentList<PhysicComponent> phy;

	public Physics() {
		phy = new ComponentList<PhysicComponent>(ComponentType.PHYSIC);
		super.addList(phy);
	}

	@Override
	protected void init() {
	}

	@Override
	public void update() {

		// Call the 'local' update methods
		for (PhysicComponent c : phy) {
			c.update();
		}

		// Collision detection
		int size = phy.size();
		boolean[][] matrix = new boolean[size][size];

		for (int pp = 0; pp < size; pp++) {
			PhysicComponent p = phy.get(pp);
			if (p.OwnCollisionTypes.contains(CollisionUtils.STATIC))
				continue;

			// Check if we're colliding with an edge
			if (p.screenCollision) {
				int edge = CollisionUtils.getCollidingEdge(p);
				if (edge != CollisionUtils.NO_COLLISION && edge == CollisionUtils.willCollideWithEdge(p)) {
					p.setVelocity(CollisionUtils.getVelocityFor(edge, p));
					p.onCollision(null, null);
				}
			}
			
			for (int tt = 0; tt < size; tt++) {
				if (tt == pp)
					continue;

				PhysicComponent t = phy.get(tt);
				if (matrix[pp][tt])
					continue;

				matrix[pp][tt] = true;
				if (p.equals(t) || !CollisionUtils.CanCollide(p, t))
					continue;

				Vec2f mvt = checkcollision(p, t);
				if (mvt != null) {
					p.onCollision(t, mvt);
					t.onCollision(p, mvt);
				}
			}
		}

		// Move and rotate objects after all collision is done
		for (PhysicComponent c : phy) {
			c.afterUpdate();

			Transform tf = c.getTransform();
			tf.add(Vec3f.div(c.getVelocity(), 10));
			tf.setRot(Vec3f.add(tf.getRot(), new Vec3f(0, 0, c.getRotVel())));
		}
	}

	@Override
	public void cleanUp() {

	}

	/**
	 * This method checks if the object p is going to collide with the object t
	 * in the x or the y direction. <br>
	 * It does not return a proper mvt vector if there is a point to edge
	 * collision
	 * 
	 * @param p
	 * @param t
	 */
	private Vec2f checkcollision(PhysicComponent p, PhysicComponent t) {

		Transform t1 = p.getTransform();
		Transform t2 = t.getTransform();

		Vec3f pos2 = t2.getPosition();

		// in degrees
		float rot1 = t1.getRot().z % 360;
		float rot2 = t2.getRot().z % 360;

		// Object 1: calculate the corners
		float xRadius = t1.getScale().x / 2;
		float yRadius = t1.getScale().y / 2;

		Vec3f testleft = new Vec3f(-xRadius, yRadius, 0);
		Mat4 testrot = Mat4.createRotationXYZMatrix(new Vec3f(0, 0, rot1));
		Vec topLeft1 = Matrixf.mulC(testrot, (Vec) testleft);

		Vec3f testright = new Vec3f(xRadius, yRadius, 0);
		Mat4 testrotr = Mat4.createRotationXYZMatrix(new Vec3f(0, 0, rot1));
		Vec topRight1 = Matrixf.mulC(testrotr, (Vec) testright);

		Vec2f bottomLeft1 = new Vec2f(-topRight1.x, -topRight1.y);
		Vec2f bottomRight1 = new Vec2f(-topLeft1.x, -topLeft1.y);

		// NOTE: the absolute positions are calculated per velocity component

		// Object 2: calculate the corners
		float x2Radius = t2.getScale().x / 2;
		float y2Radius = t2.getScale().y / 2;

		Vec3f testleft2 = new Vec3f(-x2Radius, y2Radius, 0);
		Mat4 testrot2 = Mat4.createRotationXYZMatrix(new Vec3f(0, 0, rot2));
		Vec topLeft2 = Matrixf.mulC(testrot2, (Vec) testleft2);

		Vec3f testright2 = new Vec3f(x2Radius, y2Radius, 0);
		Vec topRight2 = new Vec(testright2.toArray());
		Mat4 testrotr2 = Mat4.createRotationXYZMatrix(new Vec3f(0, 0, rot2));
		topRight2 = Matrixf.mulC(testrotr2, (Vec) testright2);

		Vec2f bottomLeft2 = new Vec2f(-topRight2.x, -topRight2.y);
		Vec2f bottomRight2 = new Vec2f(-topLeft2.x, -topLeft2.y);

		Vec3f rTL2 = Vec3f.add(pos2, topLeft2.convertToVec3f());
		Vec3f rTR2 = Vec3f.add(pos2, topRight2.convertToVec3f());
		Vec3f rBL2 = Vec3f.add(pos2, new Vec3f(bottomLeft2, 0));
		Vec3f rBR2 = Vec3f.add(pos2, new Vec3f(bottomRight2, 0));

		// Calculate the axis
		// the absolute positions for object 1 are not known at this point, but
		// these axis are the same, just not so pretty
		Vec3f axis1 = Vec3f.sub(topLeft1.convertToVec3f(), topRight1.convertToVec3f());
		Vec3f axis2 = Vec3f.sub(topRight1.convertToVec3f(), new Vec3f(bottomRight1, 0));
		// pretty ones for object 2
		Vec3f axis3 = Vec3f.sub(rTL2, rTR2);
		Vec3f axis4 = Vec3f.sub(rTR2, rBR2);
		Vec3f[] axis = { axis1, axis2, axis3, axis4 };

		Vec3f mvtx = null;
		Vec3f mvty = null;
		// We need to know where our object 1 is relative to object 2
		boolean right = false;
		boolean top = false;
		if (t1.getPosition().x > t2.getPosition().x)
			right = true;
		if (t1.getPosition().y > t2.getPosition().y)
			top = true;

		/**
		 * We do separate checks for x and y to determine the direction in with
		 * the objects do collide. A collision in two directions at the same
		 * tick should be impossible if the objects have valid positions.
		 */
		for (int i = 0; i < 2; i++) {

			// Calculate the position for the different Vector components
			Vec3f pos1;
			if (i == 0)
				pos1 = Vec3f.add(t1.getPosition(), Vec3f.div(new Vec3f(p.getVelocity().x, 0, 0), 10));
			else
				pos1 = Vec3f.add(t1.getPosition(), Vec3f.div(new Vec3f(0, p.getVelocity().y, 0), 10));

			Vec3f rTL1 = Vec3f.add(pos1, new Vec3f(topLeft1.convertToVec3f()));
			Vec3f rTR1 = Vec3f.add(pos1, new Vec3f(topRight1.convertToVec3f()));
			Vec3f rBL1 = Vec3f.add(pos1, new Vec3f(bottomLeft1, 0));
			Vec3f rBR1 = Vec3f.add(pos1, new Vec3f(bottomRight1, 0));

			Vec2f[] corner1 = { Vec3f.convertToVec2f(rTL1), Vec3f.convertToVec2f(rTR1), Vec3f.convertToVec2f(rBL1),
					Vec3f.convertToVec2f(rBR1) };
			Vec2f[] corner2 = { Vec3f.convertToVec2f(rTL2), Vec3f.convertToVec2f(rTR2), Vec3f.convertToVec2f(rBL2),
					Vec3f.convertToVec2f(rBR2) };

			if (i == 0)
				mvtx = check(axis, corner1, corner2);
			else
				mvty = check(axis, corner1, corner2);
		}

		if (mvtx == null && mvty == null)
			return null;

		// Now determine witch direction we are colliding in and return the
		// minimum magnitude vector
		mvtx = mvtx == null ? new Vec3f(0, 0, 0) : mvtx;
		mvty = mvty == null ? new Vec3f(0, 0, 0) : mvty;

		float x = mvtx.x * 5f * (right ? 1 : -1);
		float y = mvty.y * 5f * (top ? 1 : -1);
		return new Vec2f(x, y);
	}

	/**
	 * This method loops through the axis and projects every given corner on
	 * those axis.
	 * 
	 * @param axis
	 * @param corner1
	 * @return
	 */
	private Vec3f check(Vec3f[] axis, Vec2f[] corner1, Vec2f[] corner2) {
		Vec3f mvt = new Vec3f();
		for (int i = 0; i < axis.length; i++) {
			Vec3f an = Vec3f.normalize(axis[i]);
			Vec2f p1 = pro(corner1, an);
			Vec2f p2 = pro(corner2, an);
			float o = overlap(p1, p2);
			if (o == -100000000) {
				return null;
			}

			// for the mvt we need nonzero axis => maybe sth with point edge bug
			mvt = Vec3f.add(mvt, Vec3f.mul(o, Vec3f.abs(an)));
		}

		return mvt;
	}

	private float inRange(float a, float min, float max) {
		if (a <= max && a >= min)
			return (max - a <= a - min) ? max - a : a - min;
		else
			return -10000000;
	}

	private float overlap(Vec2f a, Vec2f b) {
		float ax = inRange(a.x, b.x, b.y);
		float ay = inRange(a.y, b.x, b.y);
		float bx = inRange(b.x, a.x, a.y);
		float by = inRange(b.y, a.x, a.y);

		if (ax != -10000000 || ay != -10000000 || bx != -10000000 || by != -10000000) {
			float res = ay;
			if (ax != 0)
				res = ax;

			float tmp = by;
			if (bx != 0)
				tmp = bx;

			if (res >= ay && ay != 0)
				res = ay;
			if (tmp >= by && by != 0)
				tmp = by;

			if (tmp <= res || res == 0)
				res = tmp;

			return res;
		}

		return -100000000;
	}

	private Vec2f pro(Vec2f[] vecs, Vec3f axis) {
		float min = 1000000;
		float max = -1000000;
		for (int i = 0; i < 4; i++) {
			float p = Vec.sca(axis, new Vec3f(vecs[i], 0));
			if (p < min)
				min = p;
			if (max < p)
				max = p;
		}
		return new Vec2f(min, max);
	}
}
