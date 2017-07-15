/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;
import gameengine.components.GUIRenderable;
import gameengine.components.Renderable;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;
import gameengine.systems.graphics.GuiShader;
import gameengine.systems.graphics.StdShader;

/**
 * @author Daniel & Florian Albrecht
 *
 */
public class MasterRenderer extends EngineSystem {

	private ComponentList<Renderable> renderable;
	private ComponentList<GUIRenderable> gui;

	private StdShader stdShader;
	private GuiShader guiShader;

	public MasterRenderer() {
		renderable = new ComponentList<Renderable>(ComponentType.RENDERABLE);
		super.addList(renderable);

		gui = new ComponentList<GUIRenderable>(ComponentType.GUIRENDERABLE);
		super.addList(gui);

		stdShader = new StdShader();
		guiShader = new GuiShader();
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
		stdShader.unbind();

		guiShader.bind();
		for (GUIRenderable r : gui) {
			guiShader.updateShader(r.getTransform());
			r.render();
		}
	}

}
