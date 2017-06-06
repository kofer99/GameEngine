/**
 * @project GameMath
 * @package math
 * @filename Mat4.java
 * 
 * @author Florian Albrecht
 * @date 30.10.2016
 * @time 15:48:50
 *
 * @version 0.0
 */

package far.math.mat;

import far.math.vec.Vec;
import far.math.vec.Vec3f;

public class Mat4 extends Matrixf {

	/**
	 * @param value
	 * @param rows
	 * @param columns
	 * @param isColumnMajor
	 */
	public Mat4(float[] value, boolean isColumnMajor) {
		super(value, 4, 4, isColumnMajor);
	}

	public Mat4(Matrixf mat) {
		super(mat.getValue(), 4, 4, true);
	}

	// Matricies

	public final static Mat4 createTransformMatrix(Vec3f vec) {
		float[] res = new float[16];
		Mat4 mat = new Mat4(res, true);
		mat.setToCleanMatrix();
		mat.set(12, vec.x);
		mat.set(13, vec.y);
		mat.set(14, vec.z);
		return mat;
	}

	public final static Mat4 createScaleMatrix(Vec3f vec) {
		float[] res = new float[16];
		Mat4 mat = new Mat4(res, true);
		mat.setToCleanMatrix();
		mat.set(0, vec.x);
		mat.set(5, vec.y);
		mat.set(10, vec.z);
		return mat;
	}

	public final static Mat4 createTransformScaleMatrix(Vec3f translation, Vec3f scale) {
		float[] res = new float[16];
		Mat4 mat = new Mat4(res, true);
		mat.setToCleanMatrix();
		mat.set(12, translation.x);
		mat.set(13, translation.y);
		mat.set(14, translation.z);
		mat.set(0, scale.x);
		mat.set(5, scale.y);
		mat.set(10, scale.z);
		return mat;
	}

	public final static Mat4 createRotationXYZMatrix(Vec3f rotationsVec) {
		Vec rotations = Vec.makeRadians(rotationsVec);
		float[] res = new float[] { (cos(rotations.y) * cos(rotations.z)), cos(rotations.y) * sin(rotations.z),
				-sin(rotations.y), 0,
				sin(rotations.x) * sin(rotations.y) * cos(rotations.z) + cos(rotations.x) * -sin(rotations.z),
				sin(rotations.x) * sin(rotations.y) * sin(rotations.z) + cos(rotations.x) * cos(rotations.z),
				sin(rotations.x) * cos(rotations.y), 0,
				cos(rotations.x) * sin(rotations.y) * cos(rotations.z) + -sin(rotations.x) * -sin(rotations.z),
				cos(rotations.x) * sin(rotations.y) * sin(rotations.z) + -sin(rotations.x) * cos(rotations.z),
				cos(rotations.x) * cos(rotations.y), 0, 0, 0, 0, 1 };
		return new Mat4(res, true);
	}

	public final static Mat4 createCameraTransform(Vec3f tvec, Vec3f rvec) {
		return new Mat4(mulC(createTransformMatrix(tvec), createRotationXYZMatrix(rvec)));
	}

	public final static Mat4 createFrustum(float l, float r, float b, float t, float n, float f) {
		float[] res = new float[] { (2 * n) / (r - l), 0, 0, 0, 0, (2 * n) / (t - b), 0, 0, (r + l) / (r - l),
				(t + b) / (t - b), -(f + n) / (f - n), -1, 0, 0, -(2 * f * n) / (f - n), 0 };
		return new Mat4(res, true);
	}

	public final static Mat4 createFrustum(float angle, float ratio, float n, float f) {
		float ct = (float) Math.atan((double) angle);
		float[] res = new float[] { ct * ratio, 0, 0, 0, 0, ct, 0, 0, 0, 0, -(f + n) / (f - n), -1, 0, 0,
				-(2 * f * n) / (f - n), 0 };
		return new Mat4(res, true);
	}

	public final static Mat4 createOrtho(float l, float r, float b, float t, float n, float f) {
		float[] res = new float[] { 2 / (r - l), 0, 0, 0, 0, 2 / (t - b), 0, 0, 0, 0, -2 / (f - n), 0,
				-(r + l) / (r - l), -(t + b) / (t - b), -(f + n) / (f - n), 1 };
		return new Mat4(res, true);
	}

	public final static Mat4 lookAt(Vec3f eye, Vec3f target, Vec3f up) {

		// vec3f* cameraDirection = (eye - &target)->normalize();
		Vec3f cameraDirection = target;
		Vec3f cameraRight = new Vec3f(Vec.normalize(Vec.cro(up, cameraDirection)));
		Vec3f cameraUp = Vec.cro(cameraDirection, cameraRight);

		float[] dataA = new float[] { cameraRight.x, cameraUp.x, cameraDirection.x, 0, cameraRight.y, cameraUp.y,
				cameraDirection.y, 0, cameraRight.z, cameraUp.z, cameraDirection.z, 0, 0, 0, 0, 1 };

		float[] dataB = new float[] { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, -eye.x, -eye.y, -eye.z, 1 };

		Mat4 A = new Mat4(dataA, true);
		Mat4 B = new Mat4(dataB, true);

		Mat4 res = new Mat4(Matrixf.mulC(A, B));

		return res;
	}

	//

	private void setToCleanMatrix() {
		for (int i = 0; i < 16; i += 5) {
			this.set(i, 1);
		}
	}

	private static float cos(float rot) {
		return (float) Math.cos((double) rot);
	}

	private static float sin(float rot) {
		return (float) Math.sin((double) rot);
	}

}
