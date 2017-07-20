/**
 * 
 */
package gameengine.objects;

import gameengine.systems.graphics.Camera;

/**
 * @author Florian Albrecht
 *
 */
public interface Game {

	public static final Camera camera = new Camera();

	public void init();

	public void update();
}
