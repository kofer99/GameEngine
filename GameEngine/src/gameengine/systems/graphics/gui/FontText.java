package gameengine.systems.graphics.gui;

import java.util.ArrayList;

import far.math.vec.Vec3f;
import gameengine.systems.graphics.Mesh;

public class FontText {

	private Font font;

	private ArrayList<FontTextSequence> sequences;

	public FontText(Font font) {
		sequences = new ArrayList<FontTextSequence>();

		this.font = font;
	}

	public void addString(Vec3f position, String text) {
		sequences.add(new FontTextSequence(position, text));
	}

	public Mesh loadText() {
		ArrayList<Float> vertices = new ArrayList<Float>();
		ArrayList<Float> texCoords = new ArrayList<Float>();

		for (FontTextSequence t : sequences) {
			font.createMesh(t.getText(), t.getPosition(), vertices, texCoords);
		}

		float[] verticesArr = new float[vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			verticesArr[i] = vertices.get(i);
		}
		float[] texCoordsArr = new float[texCoords.size()];
		for (int i = 0; i < texCoords.size(); i++) {
			texCoordsArr[i] = texCoords.get(i);
		}

		// font.getTextureSource()
		Mesh mesh = new Mesh(verticesArr, texCoordsArr);

		return mesh;
	}

}
