/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;
import gameengine.components.Renderable;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;
import gameengine.systems.graphics.Shader;

/**
 * @author Daniel & Florian Albrecht
 *
 */
public class MasterRenderer extends EngineSystem {

	private ComponentList<Renderable> renderable;

	public MasterRenderer() {
		renderable = new ComponentList<Renderable>(ComponentType.RENDERABLE);
		super.addList(renderable);

		Shader.createShader();
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
