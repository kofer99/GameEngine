package gameengine.systems.graphics.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import far.math.vec.Vec3f;
import gameengine.systems.graphics.Mesh;

public class Font {

	private Map<Integer, Glyph> glyphs;

	// Info
	private String name;
	private short size;
	private short pXPadding;
	private short nXPadding;
	private short pYPadding;
	private short nYPadding;

	// Common
	private short lineHeight;
	private short base;
	private short scaleW;
	private short scaleH;

	// Page
	private String file;
	private short charsCount;

	private Map<String, String> metaData;
	private BufferedReader input;

	public Font(String name) {
		this.name = name;
		glyphs = new HashMap<Integer, Glyph>();
		metaData = new HashMap<String, String>();
		load();
	}

	private void load() {
		System.out.println("\nLoading font " + name);
		if (!openFile())
			return;
		loadHeader();
		while (loadNextLine()) {
			processChar();
		}
		System.out.println("Loaded " + glyphs.size() + " glyphs \n");
	}

	private void processChar() {
		Glyph g = new Glyph();
		g.id = Integer.parseInt(metaData.get("id"));
		g.x = Float.parseFloat(metaData.get("x")) + pXPadding;
		g.y = Float.parseFloat(metaData.get("y")) + pYPadding;
		g.width = Float.parseFloat(metaData.get("width")) - nXPadding;
		g.height = Float.parseFloat(metaData.get("height")) - nYPadding;
		g.xOffset = Float.parseFloat(metaData.get("xoffset"));
		g.yOffset = Float.parseFloat(metaData.get("yoffset"));
		g.xAdvance = Float.parseFloat(metaData.get("xadvance"));
		glyphs.put(g.id, g);
	}

	private boolean loadNextLine() {
		metaData.clear();
		String line;
		try {
			if ((line = input.readLine()) != null) {
				if (line.startsWith("char id")) {
					String[] strings = line.split(" ");
					for (int i = 0; i < strings.length; i++) {
						String[] pair = strings[i].split("=");
						if (pair.length == 2)
							metaData.put(pair[0], pair[1]);
					}
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void loadHeader() {
		try {
			String line;
			for (int n = 0; n < 4; n++) {
				if ((line = input.readLine()) != null) {
					String[] strings = line.split(" ");
					for (int i = 0; i < strings.length; i++) {
						String[] pair = strings[i].split("=");
						if (pair.length == 2)
							metaData.put(pair[0], pair[1]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.size = Short.parseShort(metaData.get("size"));
		String[] paddings = metaData.get("padding").split(",");
		// this.pYPadding = Short.parseShort(paddings[0]);
		// this.pXPadding = Short.parseShort(paddings[1]);
		// this.nYPadding = Short.parseShort(paddings[2]);
		// this.nXPadding = Short.parseShort(paddings[3]);
		System.out.println("padding = " + pYPadding + " " + pXPadding + " " + nYPadding + " " + nXPadding);

		this.lineHeight = Short.parseShort(metaData.get("lineHeight"));
		this.base = Short.parseShort(metaData.get("base"));
		this.scaleW = Short.parseShort(metaData.get("scaleW"));
		this.scaleH = Short.parseShort(metaData.get("scaleH"));

		this.file = metaData.get("file").substring(1, metaData.get("file").length() - 1);

		this.charsCount = Short.parseShort(metaData.get("count"));
	}

	private boolean openFile() {
		try {
			System.out.println("Reading fontfile: " + "res/fonts/" + name + ".fnt");
			input = new BufferedReader(new FileReader("res/fonts/" + name + ".fnt"));
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Mesh createMesh(String content, Vec3f position) {
		Vec3f cursor = new Vec3f(-1, 1, 0.0f);

		char[] chars = content.toCharArray();
		float[] vertices = new float[chars.length * 4 * 4];
		float[] texCoords = new float[chars.length * 4 * 2];
		for (int i = 0; i < chars.length; i++) {
			glyphs.get((int) chars[i]).createData(vertices, texCoords, cursor, 1.0f, scaleW, i, base);
			if (i != 0 && i % (chars.length / 2) == 0) {
				cursor.x = -2;
				base -= this.lineHeight;
			}
		}

		String s = "";
		for (int i = 0; i < vertices.length; i++) {
			if (i % 4 == 0)
				s += "; ";
			s += vertices[i] + ", ";
		}
		System.out.println(s);

		Mesh mesh = new Mesh(vertices, texCoords);
		return mesh;
	}

	public void createMesh(String content, Vec3f position, ArrayList<Float> vertices, ArrayList<Float> texCoords) {
		Vec3f cursor = new Vec3f(position);

		char[] chars = content.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == (int) '\\' && chars[i + 1] == (int) 'n') {
				cursor.x = position.x;
				base -= this.lineHeight;
				i++;
			} else {
				glyphs.get((int) chars[i]).createData(vertices, texCoords, cursor, 1.0f, scaleW, base);
			}
		}
	}

	private void print(String[] string) {
		String res = "";
		for (String s : string)
			res += s + "/";
		System.out.println(res);
	}

	public String getTextureSource() {
		return "/fonts/" + file;
	}

}