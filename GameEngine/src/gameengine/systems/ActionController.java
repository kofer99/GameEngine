/**
 * 
 */
package gameengine.systems;

import gameengine.Engine;
import gameengine.collections.ComponentList;
import gameengine.components.ActionComponent;

import gameengine.components.Transform;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
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
		System.out.println("actions " + actions.size());
	}

	@Override
	public void update() throws ClassCastException {
		for (ActionComponent i : actions) {
				i.action();
			}
		}
	}

