/**
 * 
 */
package gameengine.objects;

import gameengine.Main;

/**
 * @author Florian Albrecht
 *
 */
public class Entity {

	private int id;
	private boolean deleted = false;

	/**
	 * Calls the Main.engine.addEntity method to add itself to the EntityHandler
	 * and to get its id.
	 */
	public Entity() {
		Main.engine.addEntity(this);
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
