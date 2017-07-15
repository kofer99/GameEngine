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

	public Text(String fontname, String text, Transform transform) {
		super();

		this.add(new GUIText(text, fontname, transform, this));
	}

}
