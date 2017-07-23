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
import gameengine.util.ICollisionListener;

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
		count = 0;
		int size = phy.size();
		// boolean[][] matrix = new boolean[size][size];

		for (int p = 0; p < size; p++) {
			if (phy.get(p).CollisionTypes.contains(CollisionUtils.STATIC))
				continue;

			for (int t = 0; t < size; t++) {
				if (t == p)
					continue;

				// if (matrix[phy.get(p).getEntityID()][t.getEntityID()])
				// continue;
				//
				// matrix[p.getEntityID()][t.getEntityID()] = true;
				// if (p.equals(t) || !CollisionUtils.CanCollide(p, t))
				// continue;

				Vec2f mvt = checkcollision(phy.get(p), phy.get(t));
				if (mvt != null) {
					for (ICollisionListener i : phy.get(p).listeners)
						i.onCollision(phy.get(t), mvt);

					for (ICollisionListener j : phy.get(t).listeners)
						j.onCollision(phy.get(p), mvt);
				}
			}

		}
		// System.out.println("COUNT = " + count);
		// Move and rotate objects after all collision is done
		for (PhysicComponent c : phy) {
			Transform tf = c.getTransform();

			tf.add(Vec3f.div(c.getVelocity(), 10));
			tf.setRot(Vec3f.add(tf.getRot(), new Vec3f(0, 0, c.getRotVel())));
		}
	}

	@Override
	public void cleanUp() {

	}

	int count;

	/**
	 * This method checks if the object p is going to collide with the object t
	 * in the x or the y direction
	 * 
	 * @param p
	 * @param t
	 */
	private Vec2f checkcollision(PhysicComponent p, PhysicComponent t) {
		count++;

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
		// the absolute positions are not known at this point, but these are the
		// same, just not so pretty
		Vec3f axis1 = Vec3f.abs(Vec3f.sub(topLeft1.convertToVec3f(), topRight1.convertToVec3f()));
		Vec3f axis2 = Vec3f.abs(Vec3f.sub(topRight1.convertToVec3f(), new Vec3f(bottomRight1, 0)));
		Vec3f axis3 = Vec3f.abs(Vec3f.sub(rTL2, rTR2));
		Vec3f axis4 = Vec3f.abs(Vec3f.sub(rTR2, rBR2));
		Vec3f[] axis = { axis1, axis2, axis3, axis4 };

		Vec3f mvtx = null;
		Vec3f mvty = null;
		// We need to know where our object is relative to the other one
		boolean right = false;
		boolean top = false;
		if (t1.getPosition().x > t2.getPosition().x)
			right = true;
		if (t1.getPosition().y > t2.getPosition().y)
			top = true;

		/**
		 * We do separate checks for x and y to determine the direction in with
		 * the objects do collide. A collision in two directions at the same
		 * tick should be impossible
		 */
		for (int i = 0; i < 2; i++) {
			if (mvtx != null)
				break; // we already have a collision

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

			Vec2f[] corner1 = { Vec3f.convertToVec2f(rTL1), Vec3f.convertToVec2f(rTL2), Vec3f.convertToVec2f(rTR1),
					Vec3f.convertToVec2f(rTR2), Vec3f.convertToVec2f(rBL1), Vec3f.convertToVec2f(rBL2),
					Vec3f.convertToVec2f(rBR1), Vec3f.convertToVec2f(rBR2) };

			if (i == 0)
				mvtx = check(axis, corner1);
			else
				mvty = check(axis, corner1);
		}

		// Now determine witch diretion we are colliding in and return the
		// minimum magnitude vector
		if (mvtx != null) {
			if (right)
				return new Vec2f(mvtx.x * 5f, 0);
			else
				return new Vec2f(mvtx.x * -5f, 0);
		}
		if (mvty != null) {
			// to avoid a glitch where a falling object is going to glitch to
			// the left or right this vector gets some threshold
			// TODO: really fix this bug, its crazy
			if (top)
				return new Vec2f(0, mvty.y * 5 + 0.15f);
			else
				return new Vec2f(0, mvty.y * -5 - 0.15f);
		}

		return null;
	}

	/**
	 * This method loops through the axis and projects every given corner on
	 * those axis.
	 * 
	 * @param axis
	 * @param corner1
	 * @return
	 */
	private Vec3f check(Vec3f[] axis, Vec2f[] corner1) {
		Vec3f mvt = new Vec3f();

		for (int i = 0; i < axis.length; i++) {
			Vec3f an = Vec3f.normalize(axis[i]);
			Vec2f p1 = pro(new Vec2f[] { corner1[0], corner1[2], corner1[4], corner1[6] }, an);
			Vec2f p2 = pro(new Vec2f[] { corner1[1], corner1[3], corner1[5], corner1[7] }, an);

			float o = overlap(p1, p2);
			if (o == -100000000) {
				return null;
			}
			mvt = Vec3f.add(mvt, Vec3f.mul(o, an));
		}
		return mvt;
	}

	private float inRange(float a, float min, float max) {
		if (a <= max && a >= min)
			return (max - a <= a - min) ? max - a : a - min;
		else
			return 0;
	}

	private float overlap(Vec2f a, Vec2f b) {
		float ax = inRange(a.x, b.x, b.y);
		float ay = inRange(a.y, b.x, b.y);
		float bx = inRange(b.x, a.x, a.y);
		float by = inRange(b.y, a.x, a.y);

		if (ax != 0 || ay != 0 || bx != 0 || by != 0) {
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

	/**
	 * @param axis
	 * @param corner1
	 * @return
	 */
	private boolean checkMinMax(Vec2f[] axis, Vec2f[] corner1) {
		// 0:Minimum from object 1 1: Minimum object2 2: Max object1 3:
		// MaxObject 2
		float[][] minmax = new float[4][4];

		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 8; i++) {
				float temp;
				temp = project(axis[j], corner1[i]);
				if (i % 2 == 0) {
					if (minmax[j][0] == 0f)
						minmax[j][0] = temp;
					if (minmax[j][2] == 0f)
						minmax[j][2] = temp;

					if (minmax[j][0] > temp) {
						minmax[j][0] = temp;
						// System.out.println("Axis: "+ j + " Min object1 =
						// "+minmax[j][2]);
					} else if (minmax[j][2] < temp) {
						minmax[j][2] = temp;
						// System.out.println("Axis: "+ j + " Max object1 =
						// "+minmax[j][2]);
					}

				} else {
					if (minmax[j][1] == 0f)
						minmax[j][1] = temp;
					if (minmax[j][3] == 0f)
						minmax[j][3] = temp;

					if (minmax[j][1] > temp) {
						minmax[j][1] = temp;
						// System.out.println("Axis: "+ j + " Min object2 =
						// "+minmax[j][1]);
					} else if (minmax[j][3] < temp) {
						minmax[j][3] = temp;
						// System.out.println("Axis: "+ j + " Max object2 =
						// "+minmax[j][3]);
					}
				}

			}

			if (minmax[j][1] <= minmax[j][2] && minmax[j][3] >= minmax[j][0]) {
				continue;
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param axis1
	 * @param topLeft1
	 * @return
	 */
	private float project(Vec2f axis, Vec2f corner) {
		Vec2f temp = new Vec2f();
		temp.x = (((corner.x * axis.x) + (corner.y * axis.y)) / ((axis.x * axis.x) + (axis.y * axis.y))) * axis.x;
		temp.y = (((corner.x * axis.x) + (corner.y * axis.y)) / ((axis.x * axis.x) + (axis.y * axis.y))) * axis.y;
		// System.out.println(temp.toString());
		float tl11s = (temp.x * axis.x) + (temp.y * axis.y);
		return tl11s;
	}
}
