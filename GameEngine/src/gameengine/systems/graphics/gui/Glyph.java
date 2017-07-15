package gameengine.systems.graphics.gui;

import java.util.ArrayList;

import far.math.vec.Vec3f;

public class Glyph {

	public int id; // ASCII id
	public float x; // left position in texture
	public float y; // top position in texture
	public float width; // width in texture
	public float height; // height in texture
	public float xOffset; // offset of topLeft corner relative to the cursor
	public float yOffset; // offset of topLeft corner relative to the cursor
	public float xAdvance; // cursor movement

	public Glyph() {

	}

	public void createData(float[] vertices, float[] texCoords, Vec3f cursor, float fontSize, short dimensions,
			int arrPos, int base) {

		System.out.println("char " + id + "/" + (char) id + ", x= " + x + ", w = " + xOffset);

		float sX = xOffset * fontSize;
		float sY = yOffset * fontSize;
		float sW = width * fontSize;
		float sH = height * fontSize;
		float sB = base * fontSize;

		vertices[(arrPos * 16) + 0] = cursor.x + (sX / dimensions);
		vertices[(arrPos * 16) + 1] = cursor.y + ((sB - (sY + sH)) / dimensions);
		vertices[(arrPos * 16) + 2] = cursor.z;
		vertices[(arrPos * 16) + 3] = 1.0f;

		vertices[(arrPos * 16) + 4] = cursor.x + ((sX + sW) / dimensions);
		vertices[(arrPos * 16) + 5] = cursor.y + ((sB - (sY + sH)) / dimensions);
		vertices[(arrPos * 16) + 6] = cursor.z;
		vertices[(arrPos * 16) + 7] = 1.0f;

		vertices[(arrPos * 16) + 8] = cursor.x + ((sX + sW) / dimensions);
		vertices[(arrPos * 16) + 9] = cursor.y + ((sB - sY) / dimensions);
		vertices[(arrPos * 16) + 10] = cursor.z;
		vertices[(arrPos * 16) + 11] = 1.0f;

		vertices[(arrPos * 16) + 12] = cursor.x + (sX / dimensions);
		vertices[(arrPos * 16) + 13] = cursor.y + ((sB - sY) / dimensions);
		vertices[(arrPos * 16) + 14] = cursor.z;
		vertices[(arrPos * 16) + 15] = 1.0f;

		float tx = x / (float) dimensions;
		float ty = y / (float) dimensions;
		float tw = width / (float) dimensions;
		float th = height / (float) dimensions;

		texCoords[(arrPos * 8) + 0] = tx;
		texCoords[(arrPos * 8) + 1] = ty + th;

		texCoords[(arrPos * 8) + 2] = tx + tw;
		texCoords[(arrPos * 8) + 3] = ty + th;

		texCoords[(arrPos * 8) + 4] = tx + tw;
		texCoords[(arrPos * 8) + 5] = ty;

		texCoords[(arrPos * 8) + 6] = tx;
		texCoords[(arrPos * 8) + 7] = ty;

		// texCoords[(arrPos * 8) + 0] = 0;
		// texCoords[(arrPos * 8) + 1] = 1;
		// texCoords[(arrPos * 8) + 2] = 1;
		// texCoords[(arrPos * 8) + 3] = 1;
		// texCoords[(arrPos * 8) + 4] = 1;
		// texCoords[(arrPos * 8) + 5] = 0;
		// texCoords[(arrPos * 8) + 6] = 0;
		// texCoords[(arrPos * 8) + 7] = 0;

		cursor.x += (xAdvance / dimensions) * fontSize;
	}

	public void createData(ArrayList<Float> vertices, ArrayList<Float> texCoords, Vec3f cursor, float fontSize,
			short dimensions, int base) {

		System.out.println("char " + id + "/" + (char) id + ", x= " + x + ", w = " + xOffset);

		float sX = xOffset * fontSize;
		float sY = yOffset * fontSize;
		float sW = width * fontSize;
		float sH = height * fontSize;
		float sB = base * fontSize;

		vertices.add(cursor.x + (sX / dimensions));
		vertices.add(cursor.y + ((sB - (sY + sH)) / dimensions));
		vertices.add(cursor.z);
		vertices.add(1.0f);

		vertices.add(cursor.x + ((sX + sW) / dimensions));
		vertices.add(cursor.y + ((sB - (sY + sH)) / dimensions));
		vertices.add(cursor.z);
		vertices.add(1.0f);

		vertices.add(cursor.x + ((sX + sW) / dimensions));
		vertices.add(cursor.y + ((sB - sY) / dimensions));
		vertices.add(cursor.z);
		vertices.add(1.0f);

		vertices.add(cursor.x + (sX / dimensions));
		vertices.add(cursor.y + ((sB - sY) / dimensions));
		vertices.add(cursor.z);
		vertices.add(1.0f);

		float tx = x / (float) dimensions;
		float ty = y / (float) dimensions;
		float tw = width / (float) dimensions;
		float th = height / (float) dimensions;

		texCoords.add(tx);
		texCoords.add(ty + th);

		texCoords.add(tx + tw);
		texCoords.add(ty + th);

		texCoords.add(tx + tw);
		texCoords.add(ty);

		texCoords.add(tx);
		texCoords.add(ty);

		cursor.x += (xAdvance / dimensions) * fontSize;
	}

	public void print() {
		System.out.println("id = " + this.id);
		System.out.println("x = " + this.x);
		System.out.println("y = " + this.y);
		System.out.println("width = " + this.width);
		System.out.println("height = " + this.height);
		System.out.println("xOffset = " + this.xOffset);
		System.out.println("yOffset = " + this.yOffset);
		System.out.println("xAdvance = " + this.xAdvance);
	}
}
