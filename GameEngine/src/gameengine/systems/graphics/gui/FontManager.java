package gameengine.systems.graphics.gui;

import far.math.vec.Vec3f;
import gameengine.components.GUIText;
import gameengine.systems.graphics.Mesh;

public class FontManager {

	Font font;

	public FontManager() {

	}

	// TODO: remove
	public Mesh loadFont(GUIText guiText) {
		FontText ftext = new FontText(guiText.fontName);
		ftext.addString(new Vec3f(), guiText.text);
		return ftext.loadText();
	}

}
