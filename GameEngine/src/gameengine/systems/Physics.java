/**
 * 
 */
package gameengine.systems;

import java.util.ArrayList;
import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.collections.ComponentList;
import gameengine.collections.EntityHandler;
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

	private ComponentList<Transform> transforms;
	private ComponentList<PhysicComponent> phy;

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

	@Override
	protected void init() { }

	@Override
	public void update() {
		int size = phy.size();
		boolean[][] matrix = new boolean[size][size];

		for (PhysicComponent p : phy) {
			for (PhysicComponent t : phy) {
				if (matrix[p.getEntityID()][t.getEntityID()])
					continue;

				matrix[p.getEntityID()][t.getEntityID()] = true;
				if (p.equals(t) || !CollisionUtils.CanCollide(p, t))
					continue;

				if (checkcollision(p, t)) {
					p.ControllingPlayer.onCollision(t.ControllingPlayer);
					t.ControllingPlayer.onCollision(p.ControllingPlayer);
				}
			}

		}

		// Move and rotate objects after all collision is done
		for (PhysicComponent c : phy) {
			Transform tf = c.getTransform();

			tf.add(Vec3f.div(c.getVelocity(), 10));
			tf.setRot(Vec3f.add(tf.getRot(), new Vec3f(0, 0, c.getRotVel())));
		}
	}

	float lastDist = 0f;

	/**
	 * @param p
	 * @param t
	 */
	private boolean checkcollision(PhysicComponent p, PhysicComponent t) {
		Transform t1 = p.getTransform();
		Transform t2 = t.getTransform();

		Vec3f pos1 = t1.getPosition();
		Vec3f pos2 = t2.getPosition();

		// in degrees
		float rot1 = t1.getRot().z % 360;
		float rot2 = t2.getRot().z % 360;

		// Object 1
		float xRadius = t1.getScale().x / 2;
		float yRadius = t1.getScale().y / 2;

		Vec2f topLeft1 = new Vec2f(
				(float) Math.sqrt(yRadius * yRadius + xRadius * xRadius)
						* (float) Math.cos(Math.atan(yRadius / xRadius) + Math.toRadians(rot1 + 90)),
				(float) Math.sqrt(yRadius * yRadius + xRadius * xRadius)
						* ((float) Math.sin(Math.atan(yRadius / xRadius) + Math.toRadians(rot1 + 90))));
		Vec2f topRight1 = new Vec2f(
				(float) Math.sqrt(yRadius * yRadius + xRadius * xRadius)
						* (float) Math.cos(Math.atan(yRadius / xRadius) + Math.toRadians(rot1)),
				(float) Math.sqrt(yRadius * yRadius + xRadius * xRadius)
						* ((float) Math.sin(Math.atan(yRadius / xRadius) + Math.toRadians(rot1))));
		Vec2f bottomLeft1 = new Vec2f(-topRight1.x, -topRight1.y);
		Vec2f bottomRight1 = new Vec2f(-topLeft1.x, -topLeft1.y);

		Vec3f rTL1 = Vec3f.add(pos1, new Vec3f(topLeft1, 0));
		Vec3f rTR1 = Vec3f.add(pos1, new Vec3f(topRight1, 0));
		Vec3f rBL1 = Vec3f.add(pos1, new Vec3f(bottomLeft1, 0));
		Vec3f rBR1 = Vec3f.add(pos1, new Vec3f(bottomRight1, 0));
		// Object 2
		float x2Radius = t2.getScale().x / 2;
		float y2Radius = t2.getScale().y / 2;

		Vec2f topLeft2 = new Vec2f(
				(float) Math.sqrt(y2Radius * y2Radius + x2Radius * x2Radius)
						* (float) Math.cos(Math.atan(y2Radius / x2Radius) + Math.toRadians(rot2 + 90)),
				(float) Math.sqrt(y2Radius * y2Radius + x2Radius * x2Radius)
						* ((float) Math.sin(Math.atan(y2Radius / x2Radius) + Math.toRadians(rot2 + 90))));
		Vec2f topRight2 = new Vec2f(
				(float) Math.sqrt(y2Radius * y2Radius + x2Radius * x2Radius)
						* (float) Math.cos(Math.atan(y2Radius / x2Radius) + Math.toRadians(rot2)),
				(float) Math.sqrt(y2Radius * y2Radius + x2Radius * x2Radius)
						* ((float) Math.sin(Math.atan(y2Radius / x2Radius) + Math.toRadians(rot2))));
		Vec2f bottomLeft2 = new Vec2f(-topRight2.x, -topRight2.y);
		Vec2f bottomRight2 = new Vec2f(-topLeft2.x, -topLeft2.y);

		Vec3f rTL2 = Vec3f.add(pos2, new Vec3f(topLeft2, 0));
		Vec3f rTR2 = Vec3f.add(pos2, new Vec3f(topRight2, 0));
		Vec3f rBL2 = Vec3f.add(pos2, new Vec3f(bottomLeft2, 0));
		Vec3f rBR2 = Vec3f.add(pos2, new Vec3f(bottomRight2, 0));

		/*
		 * System.out.println(rTL1.toString() +"tl" );
		 * System.out.println(rTR1.toString() +"tr");
		 * System.out.println(rBL1.toString() +"bl");
		 * System.out.println(rBR1.toString() +"br");
		 */

		Vec3f axis1 = Vec3f.sub(rTL1, rTR1);
		Vec3f axis2 = Vec3f.sub(rTL1, rBL1);
		Vec3f axis3 = Vec3f.sub(rTL2, rTR2);
		Vec3f axis4 = Vec3f.sub(rTR2, rBR2);

		Vec2f[] axis = { Vec3f.convertToVec2f(axis1), Vec3f.convertToVec2f(axis2), Vec3f.convertToVec2f(axis3),
				Vec3f.convertToVec2f(axis4) };
		Vec2f[] corner1 = { Vec3f.convertToVec2f(rTL1), Vec3f.convertToVec2f(rTL2), Vec3f.convertToVec2f(rTR1),
				Vec3f.convertToVec2f(rTR2), Vec3f.convertToVec2f(rBL1), Vec3f.convertToVec2f(rBL2),
				Vec3f.convertToVec2f(rBR1), Vec3f.convertToVec2f(rBR2) };
		// Vec3f[] corner2 = {rTL2,rTR2,rBL2,rBR2};

		// Project eachpoint on all axis
		// Take scalar values of each projected point... they are meaningles but
		// indicate the position on the axis
		// Naming: Corner Object Axis
		// Values [axis][value] values 0: Min ob1 1:Min ob2 2. Max ob1, 3: max ob2
		return checkMinMax(axis, corner1);
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
