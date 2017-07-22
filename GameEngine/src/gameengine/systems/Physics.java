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

	private ComponentList<Transform> transforms;
	private ComponentList<PhysicComponent> phy;

	public Physics() {
		phy = new ComponentList<PhysicComponent>(ComponentType.PHYSIC);
		super.addList(phy);

		transforms = new ComponentList<Transform>(ComponentType.TRANSFORM);
		super.addList(transforms);
	}

	@Override
	protected void init() {
	}

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
					for (ICollisionListener i : p.listeners)
						i.onCollision(t);

					for (ICollisionListener j : t.listeners)
						j.onCollision(p);
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

	@Override
	public void cleanUp() {

	}

	float lastDist = 0f;

	/**
	 * @param p
	 * @param t
	 */
	private boolean checkcollision(PhysicComponent p, PhysicComponent t) {
		Transform t1 = p.getTransform();
		Transform t2 = t.getTransform();

		Vec3f pos1 = Vec3f.add(t1.getPosition(), Vec3f.div(p.getVelocity(), 10));
		Vec3f pos2 = Vec3f.add(t2.getPosition(), Vec3f.div(t.getVelocity(), 10));

		// in degrees
		float rot1 = t1.getRot().z %360;
		float rot2 = t2.getRot().z % 360;

		// Object 1
		float xRadius = t1.getScale().x / 2;
		float yRadius = t1.getScale().y / 2;
		double iRot1 =  Math.atan(yRadius/xRadius);
		
		Vec3f testleft = new Vec3f(-xRadius,yRadius,0);
		Vec topLeft1 = new Vec(testleft.toArray());
		Mat4 testrot = Mat4.createRotationXYZMatrix(new Vec3f(0,0,rot1));
		topLeft1=Matrixf.mulC(testrot, (Vec)testleft);
		
		//System.out.println(topLeft1.toString()+ " LEft");
		
		Vec3f testright = new Vec3f(xRadius,yRadius,0);
		Vec topRight1 = new Vec(testright.toArray());
		Mat4 testrotr = Mat4.createRotationXYZMatrix(new Vec3f(0,0,rot1));
		topRight1=Matrixf.mulC(testrotr, (Vec)testright);
		
	//	System.out.println(topRight1.toString()+ " Right");

		
		Vec2f bottomLeft1 = new Vec2f(-topRight1.x, -topRight1.y);
		Vec2f bottomRight1 = new Vec2f(-topLeft1.x, -topLeft1.y);
	//	System.out.println(p.getTransform().getPosition().toString());
		System.out.println(topLeft1.toString()+"OL");
		System.out.println(topRight1.toString()+"OR");
		System.out.println(bottomLeft1.toString()+"UL");
		System.out.println(bottomRight1.toString()+"UR");
		
		Vec3f rTL1 = Vec3f.add(pos1, new Vec3f(topLeft1.convertToVec3f()));
		Vec3f rTR1 = Vec3f.add(pos1, new Vec3f(topRight1.convertToVec3f()));
		Vec3f rBL1 = Vec3f.add(pos1, new Vec3f(bottomLeft1, 0));
		Vec3f rBR1 = Vec3f.add(pos1, new Vec3f(bottomRight1, 0));
		// Object 2
		float x2Radius = t2.getScale().x / 2;
		float y2Radius = t2.getScale().y / 2;

		Vec3f testleft2 = new Vec3f(-x2Radius,y2Radius,0);
		Vec topLeft2 = new Vec(testleft2.toArray());
		Mat4 testrot2 = Mat4.createRotationXYZMatrix(new Vec3f(0,0,rot2));
		topLeft2=Matrixf.mulC(testrot2, (Vec)testleft2);
		
		//System.out.println(topLeft1.toString()+ " LEft");
		
		Vec3f testright2 = new Vec3f(x2Radius,y2Radius,0);
		Vec topRight2 = new Vec(testright2.toArray());
		Mat4 testrotr2 = Mat4.createRotationXYZMatrix(new Vec3f(0,0,rot2));
		topRight2=Matrixf.mulC(testrotr2, (Vec)testright2);
		
	//	System.out.println(topRight1.toString()+ " Right");

		
		Vec2f bottomLeft2 = new Vec2f(-topRight2.x, -topRight2.y);
		Vec2f bottomRight2 = new Vec2f(-topLeft2.x, -topLeft2.y);
		
		System.out.println(topLeft2.toString()+"O2L");
		System.out.println(topRight2.toString()+"OR2");
		System.out.println(bottomLeft2.toString()+"UL2");
		System.out.println(bottomRight2.toString()+"UR2");
		
		
		Vec3f rTL2 = Vec3f.add(pos2, topLeft2.convertToVec3f());
		Vec3f rTR2 = Vec3f.add(pos2, topRight2.convertToVec3f());
		Vec3f rBL2 = Vec3f.add(pos2, new Vec3f(bottomLeft2, 0));
		Vec3f rBR2 = Vec3f.add(pos2, new Vec3f(bottomRight2, 0));

		// System.out.println(rTL2.toString() + "tl");
		// System.out.println(rTR2.toString() + "tr");
		// System.out.println(rBL2.toString() + "bl");
		// System.out.println(rBR2.toString() + "br");

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
		// Values [axis][value] values 0: Min ob1 1:Min ob2 2. Max ob1, 3: max
		// ob2
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
