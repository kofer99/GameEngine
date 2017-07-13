/**
 * 
 */
package gameengine.systems;

import java.util.ArrayList;
import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.collections.ComponentList;
import gameengine.collections.EntityHandler;
import gameengine.components.ActionComponent;
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
	private boolean[] collision = new boolean[4];

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
		Vec3f vel1 = new Vec3f(0, 0, 0);
		Vec3f vel2 = new Vec3f(0, 0, 0);
		boolean[][] matrix = new boolean[phy.size()][phy.size()];
		boolean cancelRotation = false;

		for (PhysicComponent p : phy) {
			// System.out.println("test " + p.getVelocity().toString());
			for (PhysicComponent t : phy) {
				vel1 = p.getVelocity();
				vel2 = t.getVelocity();
				if (!matrix[p.getEntityID()][t.getEntityID()]) {
					matrix[p.getEntityID()][t.getEntityID()] = true;
					if (p.isCollidable() == t.isCollidable() && !p.equals(t)) {
						if (checkcollision(p, t)) {
							// HACK: Cancel any rotation when we're already colliding
							cancelRotation = true;

							// Collision Response
							// Check for Movement in the same direction
							// check if Distance Increases or not
							if (Math.abs(p.getTransform().getPosition().x - t.getTransform().getPosition().x)
									- Math.abs(p.getTransform().getPosition().x + (p.getVelocity().x)
											- (t.getTransform().getPosition().x + (t.getVelocity().x))) >= 0) {
								
								double absPX = Math.abs(p.getVelocity().x);
								double absTX = Math.abs(t.getVelocity().x);

								if (p.getVelocity().x == -t.getVelocity().x) {
									vel1.x = 0;
									vel2 = new Vec3f(0, t.getVelocity().y, t.getVelocity().z);
									t.setVelocity(vel2);
								} else if (absPX < absTX) {
									vel1.x = t.getVelocity().x;
								} else if(absPX > absTX) {
									vel2 = new Vec3f(p.getVelocity().x, t.getVelocity().y, t.getVelocity().z);
									t.setVelocity(vel2);
								}
							}

							if (Math.abs(p.getTransform().getPosition().y - t.getTransform().getPosition().y)
									- Math.abs(p.getTransform().getPosition().y + (p.getVelocity().y)
											- (t.getTransform().getPosition().y + (t.getVelocity().y))) >= 0) {

								double absPY = Math.abs(p.getVelocity().y);
								double absTY = Math.abs(t.getVelocity().y);

								if (p.getVelocity().y == -t.getVelocity().y) {
									vel1.y = 0;
									vel2 = new Vec3f(t.getVelocity().x, 0, t.getVelocity().z);
									t.setVelocity(vel2);
								} else if (absPY < absTY) {
									vel1.y = t.getVelocity().y;
								} else if(absPY > absTY) {
									vel2 = new Vec3f(t.getVelocity().y, p.getVelocity().y, t.getVelocity().z);
									t.setVelocity(vel2);
								}
							}

							// Don't push another object out of the map
							// TODO: This is crap and should be moved onto the object itself
							boolean pBounds = checkBoundaries(p);
							if (checkBoundaries(t) || pBounds) {
								vel1 = new Vec3f(0, 0, 0);
								vel2 = new Vec3f(0, 0, 0);
								t.setVelocity(vel2);
							}
						}
					}
				}
			}

			p.getTransform().add(Vec3f.div(vel1, 10));

			if (!cancelRotation)
				p.getTransform().setRot(Vec3f.add(p.getTransform().getRot(), new Vec3f(0, 0, p.getRotVel())));
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
		if (checkMinMax(axis, corner1)) {
			System.out.println("Collision!!!");
			return true;
		}
		return false;

		/*
		 * collision[0] = false; collision[1] = false; collision[2] = false;
		 * collision[3] = false;
		 * 
		 * collision = checkcoll(minmax, collision);
		 * 
		 * if (collision[0] && collision[1] && collision[2] && collision[3]) {
		 * System.out.println("Collision!!!"); }
		 */

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

	private boolean checkBoundaries(PhysicComponent ph) {

		// Should be changed as well when changed in the player class!
		float mxr = 15.5f;
		float myr = 8.5f;
		Vec3f mov = new Vec3f();

		if (ph.getTransform().getPosition().x >= mxr && ph.getVelocity().x > 0) {
			mov.y = ph.getVelocity().y;
			mov.x = 0;
			mov.z = 0;
			ph.setVelocity(mov);
			return true;
		}

		if (ph.getTransform().getPosition().x <= -mxr && ph.getVelocity().x < 0) {
			mov.y = ph.getVelocity().y;
			mov.x = 0;
			mov.z = 0;
			ph.setVelocity(mov);
			return true;
		}

		if (ph.getTransform().getPosition().y >= myr && ph.getVelocity().y > 0) {
			mov.x = ph.getVelocity().x;
			mov.y = 0;
			mov.z = 0;
			ph.setVelocity(mov);
			return true;
		}

		if (ph.getTransform().getPosition().y <= -myr && ph.getVelocity().y < 0) {
			mov.x = ph.getVelocity().x;
			mov.y = 0;
			mov.z = 0;
			ph.setVelocity(mov);
			return true;
		}

		return false;
	}
}
