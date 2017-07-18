package gameengine.components;

import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.objects.Entity;
import gameengine.systems.graphics.Mesh;
import gameengine.systems.graphics.gui.FontManager;

public class GUIText extends Component {

	private static final FontManager fontManager = new FontManager();

	public String text;
	public String fontName;
	private Transform transform;
	private GUIRenderable grenderable;

	public GUIText(String text, String fontName, Transform transform, Entity entity, Vec3f color) {
		super(ComponentType.GUITEXT);
		this.text = text;
		this.fontName = fontName;
		this.transform = transform;

		Mesh m = fontManager.loadFont(this);

		grenderable = new GUIRenderable("fontBitMaps/" + fontName + ".png", transform, m, color);

		entity.add(transform);
		entity.add(grenderable);
	}

	public void update(String text) {
		this.text = text;
		Mesh m = fontManager.loadFont(this);
		grenderable.update(m);
	}

	public void updateColor(Vec3f ncolor) {
		grenderable.setColor(ncolor);
	}

	public Transform getTransform() {
		return transform;
	}

}
