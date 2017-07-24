/**
 * 
 */
package gameengine.objects;

import java.util.ArrayList;

import gameengine.Main;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;

/**
 * This class represents a Entity, witch is only and identifier.<br>
 * It also handles the connection to the engine to make the usage easier and
 * clearer.
 * 
 * @author Florian Albrecht
 *
 */
public class Entity {

	private int id;

	private boolean deleted = false;

	/**
	 * Adds itself to the Engine to get its id.
	 */
	public Entity() {
		Main.engine.addEntity(this);
	}

	/**
	 * This method sets the Components EntityID and adds it to the Engine.
	 * 
	 * @param component
	 */

	public Renderable renderable;
	public Transform transform;
	public PhysicComponent pysics;
	public ActionComponent action;
	public ArrayList<AudioComponent> audio = new ArrayList<AudioComponent>();

	public void add(Component component) {
		component.setEntityID(id);
		switch (component.getType()) {
		case RENDERABLE:
			this.renderable = (Renderable) component;
			break;
		case TRANSFORM:
			this.transform = (Transform) component;
			break;
		case PHYSIC:
			this.pysics = (PhysicComponent) component;
			break;
		case ACTION:
			this.action = (ActionComponent) component;
			break;
		case AUDIO:
			this.audio.add((AudioComponent) component);
			break;
		default:
			break;
		}

		Main.engine.addComponent(component);
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The id of the Entity
	 */
	public int getID() {
		return id;
	}

	/**
	 * Sets the deleted flag to true.
	 */
	public void setDeleted() {
		deleted = true;
	}

	/**
	 * Deletes this entity;
	 */
	public void delete() {
		Main.engine.removeEntity(id);
	}

	/**
	 * 
	 * @return The deleted flag
	 */
	public boolean getDeleted() {
		return deleted;
	}
}
