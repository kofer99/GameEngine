/**
 * 
 */
package gameengine.systems;

import gameengine.Engine;
import gameengine.collections.ComponentList;
import gameengine.components.InputComponent;
import gameengine.components.Transform;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
 *
 */
public class InputController extends EngineSystem {

	private ComponentList<InputComponent> inputs;

	public InputController() {
		inputs = new ComponentList<InputComponent>(ComponentType.INPUT);
		super.addList(inputs);
	}

	@Override
	protected void init() {
		System.out.println("inputs " + inputs.size());
	}

	@Override
	public void update() throws ClassCastException {
		for (InputComponent i : inputs) {
			for (int key : i.getKeys()) {
				if (Engine.keyboard.isDown(key)) {
					i.action(key);
				}
			}
		}
	}
}
