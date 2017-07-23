/**
 * @project RunEngine_C1
 * @package runEngine.math
 * @filename Matrixf.java
 * 
 * @author Florian Albrecht
 * @date 20.06.2016
 * @time 14:08:19
 *
 * @version 0.0
 */

package far.math.mat;

import far.math.vec.Vec;
import math.Vectorf;

public class Matrixf {

	private float[] value; // should be final
	private int rows, columns;
	private boolean isColumnMajor;

	/**
	 * 
	 * @param value
	 *            The data of the matrix
	 * @param rows
	 *            The amount of rows
	 * @param columns
	 *            The amount of columns
	 * @param makeColumnMajor
	 *            If the matrix is Row-Major or Column-Major
	 */

	public Matrixf(float[] value, int rows, int columns, boolean isColumnMajor) {
		if (value.length != (rows * columns))
			System.err.println("Invalid matrix.");
		this.value = value;
		this.rows = rows;
		this.columns = columns;
		this.setColumnMajor(isColumnMajor);
	}

	public void makeColumnMajor() {
		float[] tmpValue = new float[value.length];
		int tmpRows = rows, tmpColumns = columns;
		int e = 0;
		for (int c = 0; c < tmpColumns; c++) {
			for (int j = 0; j < tmpRows; j++) {
				tmpValue[e] = value[c + j * tmpColumns];
				e++;
			}
		}
		this.value = tmpValue;
		this.rows = tmpColumns;
		this.columns = tmpRows;
		this.setColumnMajor(true);
	}

	// Getters

	public float[] getValue() {
		return value;
	}

	public int getSize() {
		return value.length;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public float get(int index) {
		if (index < value.length)
			return value[index];
		else {
			System.err.println("Index out of bounds in Matrixf.get(" + index + ")");
			return 0.0f;
		}
	}

	// Setters

	public void set(int index, float newValue) {
		if (index <= value.length) {
			value[index] = newValue;
		} else {
			System.err.println("Index out of bounds in Matrixf.set(" + index + ", " + newValue + ")");
		}
	}

	// basic calculation methods

	public static Matrixf add(Matrixf matA, Matrixf matB) {
		if (matA.getRows() != matB.getRows() || matA.getColumns() != matB.getColumns()) {
			System.err.println("Addition of unequal matricies.");
			return null;
		}

		float[] res = new float[matA.getSize()];
		for (int i = 0; i < res.length; i++) {
			res[i] = matA.value[i] + matB.value[i];
		}

		return new Matrixf(res, matA.getRows(), matA.getColumns(), false);
	}

	public static Matrixf sub(Matrixf matA, Matrixf matB) {
		if (matA.getRows() != matB.getRows() || matA.getColumns() != matB.getColumns()) {
			System.err.println("Subtraction of unequal matricies.");
			return null;
		}

		float[] res = new float[matA.getSize()];
		for (int i = 0; i < res.length; i++) {
			res[i] = matA.value[i] - matB.value[i];
		}

		return new Matrixf(res, matA.getRows(), matA.getColumns(), false);
	}

	public static Matrixf mul(float factor, Matrixf mat) {
		float[] res = new float[mat.getSize()];

		for (int i = 0; i < res.length; i++) {
			res[i] = factor * mat.value[i];
		}

		return new Matrixf(res, mat.getRows(), mat.getColumns(), false);
	}

	public static Matrixf div(float factor, Matrixf mat) {
		float[] res = new float[mat.getSize()];

		for (int i = 0; i < res.length; i++) {
			res[i] = mat.value[i] / factor;
		}

		return new Matrixf(res, mat.getRows(), mat.getColumns(), false);
	}

	public static Vec mulC(Matrixf mat, Vec vec) {
		float[] res = new float[mat.getColumns()];

		for (int i = 0; i < mat.getColumns(); i++) {
			for (int j = 0; j < mat.getRows(); j++) {
				res[i] += mat.value[i + j * mat.getColumns()] * vec.get(j);
			}
		}

		return new Vec(res);
	}

	public static Matrixf mulR(Matrixf matA, Matrixf matB) {
		int rA = matA.getRows();
		int cA = matA.getColumns();
		int rB = matB.getRows();
		int cB = matB.getColumns();

		System.out.println("matA: (" + rA + ", " + +cA + "), matB: (" + rB + ", " + cB + ")\n");
		if (cA != rB) {
			System.err.println("Multiplication of incompatiple matricies");
			return null;
		}

		float[] res = new float[rA * cB];

		for (int i = 0; i < cB; i++) {
			for (int j = 0; j < rA; j++) {
				float sum = 0;
				for (int k = 0; k < cA; k++) {
					sum += matA.value[j * cA + k] * matB.value[i + k * cB];
					// System.out.println(
					// "A: " + matA.value[j * cA + k] + "; B: " + matB.value[i +
					// k * cB] + "; sum: " + sum);
				}
				res[j * cB + i] = sum;
				// System.out.println();
			}
		}

		return new Matrixf(res, rA, cB, false);
	}

	public static Matrixf mulC(Matrixf matA, Matrixf matB) {
		int rA = matA.getRows();
		int cA = matA.getColumns();
		int rB = matB.getRows();
		int cB = matB.getColumns();

		// System.out.println("matA: (" + rA + ", " + +cA + "), matB: (" + rB +
		// ", " + cB + ")\n");
		if (rA != cB) {
			System.err.println("Multiplication of incompatiple matricies");
			return null;
		}

		float[] res = new float[cA * rB];

		for (int i = 0; i < rB; i++) {
			for (int j = 0; j < cA; j++) {
				float sum = 0;
				// System.out.println("i = " + i + ", j = " + j);
				for (int k = 0; k < rA; k++) {
					// System.out.println("k = " + k);

					sum += matA.value[k * cA + j] * matB.value[k + i * cB];
					// System.out.println(
					// "A: " + matA.value[k * cA + j] + "; B: " + matB.value[k +
					// i * cB] + "; sum: " + sum);
				}
				res[i * cA + j] = sum;
				// System.out.println();
			}
		}

		return new Matrixf(res, rB, cA, false);
	}

	// Test stuff

	public void print() {
		String res = "{";
		for (int i = 0; i < rows; i++) {
			res += "\n  ";
			for (int j = 0; j < columns; j++) {
				res += value[i * columns + j] + " ";
			}
		}
		res += "\n}";
		System.out.println(res);
	}

	public boolean isColumnMajor() {
		return isColumnMajor;
	}

	public void setColumnMajor(boolean isColumnMajor) {
		this.isColumnMajor = isColumnMajor;
	}

}
