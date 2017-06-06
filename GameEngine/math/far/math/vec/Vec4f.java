/**
 * @project RunEngine_C1
 * @package runEngine.math
 * @filename Vec4f.java
 * 
 * @author Florian Albrecht
 * @date 29.06.2016
 * @time 22:41:36
 *
 * @version 0.0
 */

package far.math.vec;

public class Vec4f extends Vec {

	/**
	 * 
	 */
	public Vec4f() {

	}

	public Vec4f(Vec vec) {
		super(vec.x, vec.y, vec.z, vec.w);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vec4f(float x, float y) {
		super(x, y, 0, 0);
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vec4f(float x, float y, float z) {
		super(x, y, z, 0);
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public Vec4f(float x, float y, float z, float w) {
		super(x, y, z, w);
	}

}
