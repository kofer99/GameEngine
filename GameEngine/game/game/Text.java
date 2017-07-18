/**
 * 
 */
package game;

import gameengine.components.GUIText;
import gameengine.components.Transform;
import gameengine.objects.Entity;

/**
 * @author Florian Albrecht
 *
 */
public class Text extends Entity {

	private GUIText gtext;

	public Text(String fontname, String text, Transform transform) {
		super();

		gtext = new GUIText(text, fontname, transform, this);

		this.add(gtext);
	}

	public void update(String text) {
		gtext.update(text);
	}

}
