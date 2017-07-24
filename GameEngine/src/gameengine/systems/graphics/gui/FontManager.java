package gameengine.systems.graphics.gui;

import java.util.HashMap;

import far.math.vec.Vec3f;
import gameengine.components.GUIText;
import gameengine.systems.graphics.Mesh;

public class FontManager {

	public FontManager() {
	}

	private HashMap<String, Font> fonts = new HashMap<String, Font>();

	public Mesh loadFont(GUIText guiText) {
		Font font = null;
		if (fonts.containsKey(guiText.fontName)) {
			font = fonts.get(guiText.fontName);
		} else {
			font = new Font(guiText.fontName);
			fonts.put(guiText.fontName, font);
		}

		FontText ftext = new FontText(font);
		ftext.addString(new Vec3f(), guiText.text);
		return ftext.loadText();
	}

}
