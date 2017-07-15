package gameengine.systems.graphics.gui;

import far.math.vec.Vec3f;

public class FontTextSequence {

	private Vec3f position;
	private String text;

	public FontTextSequence(Vec3f position, String text) {
		this.position = position;
		this.text = text;
	}

	public Vec3f getPosition() {
		return position;
	}

	public String getText() {
		return text;
	}

}
