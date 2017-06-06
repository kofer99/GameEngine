/**
 * @project RunEngine_C1
 * @package runEngine.math
 * @filename Vec2f.java
 * 
 * @author Florian Albrecht
 * @date 29.06.2016
 * @time 22:41:06
 *
 * @version 1.0
 */

package far.math.vec;

/**
 * This is a subclass of the {@link Vec} class, representing a two-dimensional
 * vector. <br>
 * It has own methods for addition, subtraction, multiplication and division.
 * 
 * @version 1.0
 * @author Florian Albrecht
 */
public class Vec2f extends Vec {

	/**
	 * Creates a zeroed Vec2f.
	 */
	public Vec2f() {

	}

	/**
	 * Copies the passed in vector into a Vec2f.
	 * 
	 * @param vec
	 *            Vector to copy
	 */
	public Vec2f(Vec vec) {
		super(vec.x, vec.y);
	}

	/**
	 * Creates a Vec2f out of the passed in floats.
	 * 
	 * @param x
	 * @param y
	 */
	public Vec2f(float x, float y) {
		super(x, y);
	}

	/**
	 * Creates a Vec2f out of the passed in floats.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 *            <em>gets ignored</em>
	 */
	public Vec2f(float x, float y, float z) {
		super(x, y);
	}

	/**
	 * Creates a Vec2f out of the passed in floats.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 *            <em>gets ignored</em>
	 * @param w
	 *            <em>gets ignored</em>
	 */
	public Vec2f(float x, float y, float z, float w) {
		super(x, y);
	}

	/**
	 * Adds the two passed in vectors together and stores the result in a new
	 * Vec2f. <br>
	 * The passed in data does'nt get effected.
	 * 
	 * @param vecA
	 * @param vecB
	 * @return A Vec2f containing the result
	 */
	public static Vec2f add(Vec2f vecA, Vec2f vecB) {
		return new Vec2f(vecA.x + vecB.x, vecA.y + vecB.y);
	}

	/**
	 * Subtracts the second vector from the first vector and stores the result
	 * in a new Vec2f. <br>
	 * The passed in data does'nt get effected.
	 * 
	 * @param vecA
	 * @param vecB
	 * @return A Vec2f containing the result
	 */
	public static Vec2f sub(Vec2f vecA, Vec2f vecB) {
		return new Vec2f(vecA.x - vecB.x, vecA.y - vecB.y);
	}

	/**
	 * Multiplies the passed in vector with the factor and stores the result in
	 * a new Vec2f. <br>
	 * The passed in data does'nt get effected.
	 * 
	 * @param vec
	 * @param factor
	 * @return A Vec2f containing the result
	 */
	public static Vec2f mul(float factor, Vec2f vec) {
		return new Vec2f(vec.x * factor, vec.y * factor);
	}

	/**
	 * Divides the passed in vector with the factor and stores the result in a
	 * new Vec2f. <br>
	 * The passed in data does'nt get effected.
	 * 
	 * @param vec
	 * @param factor
	 * @return A Vec2f containing the result
	 */
	public static Vec2f div(Vec2f vec, float factor) {
		return new Vec2f(vec.x / factor, vec.y / factor);
	}
}
