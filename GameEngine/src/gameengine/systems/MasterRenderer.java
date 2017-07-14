/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;
import gameengine.components.Renderable;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;
import gameengine.systems.graphics.Shader;
import gameengine.systems.graphics.StdShader;

/**
 * @author Daniel & Florian Albrecht
 *
 */
public class MasterRenderer extends EngineSystem {

	private ComponentList<Renderable> renderable;

	private StdShader stdShader;

	public MasterRenderer() {
		renderable = new ComponentList<Renderable>(ComponentType.RENDERABLE);
		super.addList(renderable);

		stdShader = new StdShader();
	}

	@Override
	protected void init() {

	}

	// AKA render()
	@Override
	public void update() throws ClassCastException {
		stdShader.bind();
		for (Renderable r : renderable) {
			stdShader.updateShader(r.getTransform());
			r.render();
		}
	}

}
