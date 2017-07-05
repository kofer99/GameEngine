/**
 * 
 */
package gameengine.systems;

import org.lwjgl.opengl.GL11;

import gameengine.collections.ComponentList;
import gameengine.components.Renderable;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;
import gameengine.systems.graphics.Shader;

/**
 * @author Daniel
 *
 */
public class MasterRenderer extends EngineSystem {

	private Shader shader;
	private ComponentList<Renderable> renderable;

	public MasterRenderer() {
		renderable = new ComponentList<Renderable>(ComponentType.RENDERABLE);
		super.addList(renderable);

		shader = new Shader();
		shader.createShader();
	}

	@Override
	protected void init() {

	}

	// AKA render()
	@Override
	public void update() throws ClassCastException {
		for (Renderable r : renderable) {
			r.render();
		}

	}

}
