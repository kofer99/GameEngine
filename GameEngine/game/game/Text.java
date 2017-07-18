/**
 * 
 */
package game;

import far.math.vec.Vec3f;
import gameengine.components.GUIText;
import gameengine.components.Transform;
import gameengine.objects.Entity;

/**
 * @author Florian Albrecht
 *
 */
public class Text extends Entity {

	private GUIText gtext;

	public Text(String fontname, String text, Transform transform, Vec3f color) {
		super();

		gtext = new GUIText(text, fontname, transform, this, color);

		this.add(gtext);
	}

	public void update(String text) {
		gtext.update(text);
	}

	/**
	 * @param vec3f
	 */
	public void updateColor(Vec3f ncolor) {
		gtext.updateColor(ncolor);
	}

}
