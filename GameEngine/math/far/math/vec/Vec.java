/**
 * @project RunEngine_C1
 * @package runEngine.math
 * @filename Vector.java
 * 
 * @author Florian Albrecht
 * @date 19.06.2016
 * @time 15:10:51
 *
 * @version 0.0
 */

package far.math.vec;

public class Vec {

	public float x = 0, y = 0, z = 0, w = 0;

	// Constructors

	public Vec() {

	}
	public Vec(float[] vec) {
        this.x = vec[0];
        this.y = vec[1];
        this.z = vec[2];
        this.w = vec[3];
    }

	public Vec(Vec vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = vec.w;
	}

	public Vec(float f) {
		this.x = f;
		this.y = f;
		this.z = f;
		this.w = f;
	}

	public Vec(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vec(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	// Basic calculation methods, all static

	public static Vec add(Vec vecA, Vec vecB) {
		return new Vec(vecA.x + vecB.x, vecA.y + vecB.y, vecA.z + vecB.z, vecA.w + vecB.w);
	}

	public static Vec sub(Vec vecA, Vec vecB) {
		return new Vec(vecA.x - vecB.x, vecA.y - vecB.y, vecA.z - vecB.z, vecA.w - vecB.w);
	}

	public static Vec mul(float factor, Vec vec) {
		return new Vec(vec.x * factor, vec.y * factor, vec.z * factor, vec.w * factor);
	}

	public static Vec div(Vec vec, float factor) {
		return new Vec(vec.x / factor, vec.y / factor, vec.z / factor, vec.w / factor);
	}

	public static float sca(Vec vecA, Vec vecB) {
		return vecA.x * vecB.x + vecA.y * vecB.y * vecA.z * vecB.z + vecA.w * vecB.w;
	}

	public static Vec3f cro(Vec3f vecA, Vec3f vecB) {
		float x = vecA.y * vecB.z - vecA.z * vecB.y;
		float y = vecA.z * vecB.x - vecA.x * vecB.z;
		float z = vecA.x * vecB.y - vecA.y * vecB.x;
		return new Vec3f(x, y, z);
	}

	public static float length(Vec vec) {
		return (float) Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z + vec.w * vec.w);
	}

	// Complex calculation methods

	public static Vec normalize(Vec vec) {
		float length = length(vec);
		return new Vec(vec.x / length, vec.y / length, vec.z / length, vec.w / length);
	}

	public static float getAngleBetween(Vec vecA, Vec vecB) {
		return (float) Math.acos(sca(vecA, vecB) / (length(vecA) * length(vecB)));
	}

	public static Vec getMiddleof(Vec vecA, Vec vecB) {
		return mul(0.5f, add(vecA, vecB));
	}

	// Conversion methods

	public static Vec makeRadians(Vec vec) {
		return new Vec((float) Math.toRadians(vec.x), (float) Math.toRadians(vec.y), (float) Math.toRadians(vec.z),
				(float) Math.toRadians(vec.w));
	}

	public static Vec makeDegrees(Vec vec) {
		return new Vec((float) Math.toDegrees(vec.x), (float) Math.toDegrees(vec.y), (float) Math.toDegrees(vec.z),
				(float) Math.toDegrees(vec.w));
	}

	public static Vec invert(Vec vec) {
		return Vec.mul(-1.0f, vec);
	}

	public void invert() {
		x *= -1;
		y *= -1;
		z *= -1;
		w *= -1;
	}

	public void zero() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}

	public Vec clip(Vec vec, float clip) {
		if (vec.x > clip)
			vec.x = clip;
		if (vec.y > clip)
			vec.y = clip;
		if (vec.z > clip)
			vec.z = clip;
		if (vec.w > clip)
			vec.w = clip;
		return new Vec(vec.x, vec.y, vec.z, vec.w);
	}

	public void clip(float clip) {
		if (x > clip)
			x = clip;
		if (y > clip)
			y = clip;
		if (z > clip)
			z = clip;
		if (w > clip)
			w = clip;
	}

	public float[] toArray() {
		return new float[] { x, y, z, w };
	}

	public static Vec2f convertToVec2f(Vec vec) {
		return new Vec2f(vec);
	}

	public Vec2f convertToVec2f() {
		return new Vec2f(this);
	}

	public static Vec3f convertToVec3f(Vec vec) {
		return new Vec3f(vec);
	}

	public Vec3f convertToVec3f() {
		return new Vec3f(this);
	}

	public static Vec4f convertToVec4f(Vec vec) {
		return new Vec4f(vec);
	}

	public Vec4f convertToVec4f() {
		return new Vec4f(this);
	}

	public boolean isEqual(Vec vec) {
		if (x == vec.x && y == vec.y && z == vec.z && w == vec.w)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}

	// Test stuff
	public void print() {
		System.out.println("Value: " + x + ", " + y + ", " + z + ", " + w);
	}

	/**
	 * @param j
	 * @return
	 */
	public float get(int i) {
		switch (i) {
		case 0:
			return x;
		case 1:
			return y;
		case 2:
			return z;
		case 3:
			return w;
		}
		return 0;
	}
}
