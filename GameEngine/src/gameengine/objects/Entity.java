/**
 * 
 */
package gameengine.objects;

import gameengine.Main;

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
	public void add(Component component) {
		component.setEntityID(id);
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
	 * 
	 * @return The deleted flag
	 */
	public boolean getDeleted() {
		return deleted;
	}
}
