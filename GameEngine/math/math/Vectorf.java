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

package math;

public class Vectorf {

	private float[] value;

	// Constructors

	public Vectorf(float[] value) {
		this.value = value;
	}

	public Vectorf(float f1, float f2) {
		value = new float[] { f1, f2 };
	}

	public Vectorf(float f1, float f2, float f3) {
		value = new float[] { f1, f2, f3 };
	}

	public Vectorf(float f1, float f2, float f3, float f4) {
		value = new float[] { f1, f2, f3, f4 };
	}

	// Getters

	public float[] getValue() {
		return value;
	}

	public int getSize() {
		return value.length;
	}

	public float get(int i) {
		if (i < value.length)
			return value[i];
		else {
			System.err.println("Unable to get " + i + ", to small (" + value.length + ")");
			return 0.0f;
		}
	}

	public float getX() {
		if (value.length > 0)
			return value[0];
		else {
			System.err.println("Unable to get X, to small (" + value.length + ")");
			return 0.0f;
		}
	}

	public float getY() {
		if (value.length > 1)
			return value[1];
		else {
			System.err.println("Unable to get Y, to small (" + value.length + ")");
			return 0.0f;
		}
	}

	public float getZ() {
		if (value.length > 0)
			return value[2];
		else {
			System.err.println("Unable to get Z, to small (" + value.length + ")");
			return 0.0f;
		}
	}

	public float getW() {
		if (value.length > 3)
			return value[3];
		else {
			System.err.println("Unable to get W, to small (" + value.length + ")");
			return 0.0f;
		}
	}

	public float getR() {
		if (value.length > 0)
			return value[0];
		else {
			System.err.println("Unable to get R, to small (" + value.length + ")");
			return 0.0f;
		}
	}

	public float getG() {
		if (value.length > 1)
			return value[1];
		else {
			System.err.println("Unable to get G, to small (" + value.length + ")");
			return 0.0f;
		}
	}

	public float getB() {
		if (value.length > 0)
			return value[2];
		else {
			System.err.println("Unable to get B, to small (" + value.length + ")");
			return 0.0f;
		}
	}

	public float getA() {
		if (value.length > 3)
			return value[3];
		else {
			System.err.println("Unable to get A, to small (" + value.length + ")");
			return 0.0f;
		}
	}

	// Basic calculation methods, all static

	public static Vectorf add(Vectorf vectorA, Vectorf vectorB) {
		if (vectorA.getSize() != vectorB.getSize()) {
			System.err.println("Addition of unequal Vectors.");
			return new Vectorf(new float[] {});
		}

		float[] res = new float[vectorA.getSize()];

		for (int i = 0; i < res.length; i++) {
			res[i] = vectorA.value[i] + vectorB.value[i];
		}

		return new Vectorf(res);
	}

	public static Vectorf sub(Vectorf vectorA, Vectorf vectorB) {
		if (vectorA.getSize() != vectorB.getSize()) {
			System.err.println("Subtracion of unequal Vectors.");
			return new Vectorf(new float[] {});
		}

		float[] res = new float[vectorA.getSize()];

		for (int i = 0; i < res.length; i++) {
			res[i] = vectorA.value[i] - vectorB.value[i];
		}

		return new Vectorf(res);
	}

	public static Vectorf mul(float factor, Vectorf vector) {
		float[] res = new float[vector.getSize()];

		for (int i = 0; i < res.length; i++) {
			res[i] = factor * vector.value[i];
		}

		return new Vectorf(res);
	}

	public static Vectorf div(Vectorf vector, float factor) {
		float[] res = new float[vector.getSize()];

		for (int i = 0; i < res.length; i++) {
			res[i] = vector.value[i] / factor;
		}

		return new Vectorf(res);
	}

	public static float sca(Vectorf vectorA, Vectorf vectorB) {
		if (vectorA.getSize() != vectorB.getSize()) {
			System.err.println("Dot product of unequal Vectors.");
			return 0.0f;
		}

		float res = 0.0f;

		for (int i = 0; i < vectorA.getSize(); i++) {
			res += vectorA.value[i] * vectorB.value[i];
		}

		return res;
	}

	public static Vectorf cro(Vectorf vectorA, Vectorf vectorB) {
		if (vectorA.getSize() == vectorB.getSize() && vectorA.getSize() == 3) {
			float[] res = new float[vectorA.getSize()];
			res[0] = vectorA.value[1] * vectorB.value[2] - vectorA.value[2] * vectorB.value[1];
			res[1] = vectorA.value[2] * vectorB.value[0] - vectorA.value[0] * vectorB.value[2];
			res[2] = vectorA.value[0] * vectorB.value[1] - vectorA.value[1] * vectorB.value[0];

			if (vectorA.getSize() > 3) {
				for (int i = 3; i < res.length; i++) {
					res[i] = vectorA.value[i];
				}
			}

			return new Vectorf(res);
		} else if (vectorA.getSize() < 3) {
			System.err.println("Cross product for to small Vectors.");
			return new Vectorf(new float[] {});
		} else {
			System.err.println("Cross product for unequal Vectors.");
			return new Vectorf(new float[] {});
		}
	}

	public static float length(Vectorf vector) {
		float res = 0.0f;
		for (int i = 0; i < vector.getSize(); i++) {
			res += vector.value[i] * vector.value[i];
		}

		return (float) Math.sqrt(res);
	}

	// Complex calculation methods

	public static Vectorf normalize(Vectorf vector) {
		float[] res = new float[vector.getSize()];
		float length = length(vector);

		for (int i = 0; i < res.length; i++) {
			res[i] = vector.value[i] / length;
		}

		return new Vectorf(res);
	}

	public static float getAngleBetween(Vectorf vectorA, Vectorf vectorB) {
		return (float) Math.acos(sca(vectorA, vectorB) / (length(vectorA) * length(vectorB)));
	}

	public static Vectorf getMiddleof(Vectorf vectorA, Vectorf vectorB) {
		return mul(0.5f, add(vectorA, vectorB));
	}

	// Conversion methods

	public static Vectorf convertTo(Vectorf vec, int size) {
		float[] res = new float[size];
		if (vec.getSize() >= size) {
			for (int i = 0; i < size; i++) {
				res[i] = vec.value[i];
			}
		} else {
			for (int i = 0; i < vec.getSize(); i++) {
				res[i] = vec.value[i];
			}
		}

		return new Vectorf(res);
	}

	public static Vectorf makeRadians(Vectorf vec) {
		float[] res = new float[vec.getSize()];
		for (int i = 0; i < res.length; i++) {
			res[i] = (float) Math.toRadians((double) vec.get(i));
		}
		return new Vectorf(res);
	}

	// Test stuff
	public void print() {
		String str = "Value = ";
		for (int i = 0; i < value.length; i++) {
			str += value[i];
			if (i != value.length - 1)
				str += ", ";
		}
		System.out.println(str);
	}

	public static Vectorf invert(Vectorf vec) {
		return Vectorf.mul(-1.0f, vec);
	}
}
