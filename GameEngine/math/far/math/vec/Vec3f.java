/**
 * @project RunEngine_C1
 * @package runEngine.math
 * @filename Vec3f.java
 * 
 * @author Florian Albrecht
 * @date 29.06.2016
 * @time 22:41:16
 *
 * @version 0.0
 */

package far.math.vec;

/**
 *
 * This is a subclass of the {@link Vec} class, representing a three-dimensional
 * vector. <br>
 * It has own methods for addition, subtraction, multiplication and division.
 * 
 * @version 1.0
 * @author Florian Albrecht
 */
public class Vec3f extends Vec {

	/**
	 * Creates a zeroed Vec3f.
	 */
	public Vec3f() {

	}

	/**
	 * Creates a Vec3f with all components at the passed in value.
	 * 
	 * @param value
	 */
	public Vec3f(float value) {
		super(value, value, value);
	}

	/**
	 * Copies the passed in vector into a Vec3f.
	 * 
	 * @param vec
	 *            Vector to copy
	 */
	public Vec3f(Vec vec) {
		super(vec.x, vec.y, vec.z);
	}

	/**
	 * Copies the passed in vector into a Vec3f and copies the z value also to
	 * it.
	 * 
	 * @param vec
	 *            Vector to copy
	 */
	public Vec3f(Vec2f vec, float z) {
		super(vec.x, vec.y, z);
	}

	/**
	 * Creates a Vec3f out of the passed in floats, the 'z'-component is set to
	 * zero.
	 * 
	 * @param x
	 * @param y
	 */
	public Vec3f(float x, float y) {
		super(x, y, 0);
	}

	/**
	 * Creates a Vec3f out of the passed in floats.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vec3f(float x, float y, float z) {
		super(x, y, z);
	}

	/**
	 * Creates a Vec3f out of the passed in floats.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 *            <em>gets ignored</em>
	 */
	public Vec3f(float x, float y, float z, float w) {
		super(x, y, z);
	}

	/**
	 * Adds the two passed in vectors together and stores the result in a new
	 * Vec3f. <br>
	 * The passed in data does'nt get effected.
	 * 
	 * @param vecA
	 * @param vecB
	 * @return A Vec3f containing the result
	 */
	public static Vec3f add(Vec3f vecA, Vec3f vecB) {
		return new Vec3f(vecA.x + vecB.x, vecA.y + vecB.y, vecA.z + vecB.z);
	}

	/**
	 * Subtracts the second vector from the first vector and stores the result
	 * in a new Vec3f. <br>
	 * The passed in data does'nt get effected.
	 * 
	 * @param vecA
	 * @param vecB
	 * @return A Vec3f containing the result
	 */
	public static Vec3f sub(Vec3f vecA, Vec3f vecB) {
		return new Vec3f(vecA.x - vecB.x, vecA.y - vecB.y, vecA.z - vecB.z);
	}

	/**
	 * Multiplies the passed in vector with the factor and stores the result in
	 * a new Vec3f. <br>
	 * The passed in data does'nt get effected.
	 * 
	 * @param vec
	 * @param factor
	 * @return A Vec3f containing the result
	 */
	public static Vec3f mul(float factor, Vec3f vec) {
		return new Vec3f(vec.x * factor, vec.y * factor, vec.z * factor);
	}

	/**
	 * Divides the passed in vector with the factor and stores the result in a
	 * new Vec3f. <br>
	 * The passed in data does'nt get effected.
	 * 
	 * @param vec
	 * @param factor
	 * @return A Vec3f containing the result
	 */
	public static Vec3f div(Vec3f vec, float factor) {
		return new Vec3f(vec.x / factor, vec.y / factor, vec.z / factor);
	}

	public static Vec3f normalize(Vec3f vec) {
		float temp;
		temp = Math.abs(vec.x) + Math.abs(vec.y) + Math.abs(vec.z);
		vec = div(vec, temp);
		return vec;
	}

	public void add(Vec3f vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
	}

	public void sub(Vec3f vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
	}

	public void mul(Vec3f vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
	}

	public void div(Vec3f vec) {
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
	}

	public void mul(float f) {
		x *= f;
		y *= f;
		z *= f;
	}

	public void div(float f) {
		x /= f;
		y /= f;
		z /= f;
	}
}
