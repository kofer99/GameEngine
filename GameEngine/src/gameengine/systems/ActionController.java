
/**
 * 
 */
package gameengine.systems;

import gameengine.collections.ComponentList;
import gameengine.components.ActionComponent;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht & Daniel
 *
 */
public class ActionController extends EngineSystem {

	private ComponentList<ActionComponent> actions;

	public ActionController() {
		actions = new ComponentList<ActionComponent>(ComponentType.ACTION);
		super.addList(actions);
	}

	@Override
	protected void init() {
	}

	@Override
	public void update() throws ClassCastException {
		for (ActionComponent i : actions) {
			i.action();
		}
	}

	@Override
	public void cleanUp() {

	}
}
