/**
 * 
 */
package gameengine.collections;

import java.util.ArrayList;

import gameengine.objects.Entity;

/**
 * This class keeps track of all entities currently in the game.
 * 
 * @author Florian Albrecht
 *
 */
public class EntityList extends ArrayList<Entity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6927682181843481321L;

	/**
	 * Points to the next free id.
	 */
	private static int nextID = 0;

	/**
	 * Stores the freed ids.
	 */
	private static ArrayList<Integer> freeIDs = new ArrayList<Integer>(100);

	/**
	 * Adds an <code>Entity</code> and sets its id to the next free id or the
	 * first of the deleted ids.
	 * 
	 * @param e
	 *            The Entity to be added
	 */
	public boolean addEntity(Entity e) {
		if (freeIDs.isEmpty()) {
			e.setID(nextID++);
			return super.add(e);
		} else {
			e.setID((Integer) freeIDs.remove(freeIDs.size() - 1));
			return setEntity(e.getID(), e);
		}
	}

	/**
	 * Removes an <code>Entity</code>, by setting its status to deleted and
	 * freeing its id.
	 * 
	 * @param eID
	 *            The id of the Entity
	 * @return
	 */
	public Entity removeEntity(int eID) {
		Entity e = super.get(eID);
		e.setDeleted();
		freeIDs.add(eID);
		return e;
	}

	/**
	 * This method is a helper method, witch will be used to improve memory
	 * management.
	 * 
	 * @param i
	 *            The id to be inserted to
	 * @param e
	 *            The Entity to insert
	 * @return
	 */
	private boolean setEntity(int i, Entity e) {
		super.set(i, e);
		return true;
	}

}
