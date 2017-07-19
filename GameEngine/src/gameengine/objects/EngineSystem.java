/**
 * 
 */
package gameengine.objects;

import java.util.ArrayList;

import gameengine.collections.CList;
import gameengine.collections.EntityHandler;

/**
 * This class is the super class for every System in the Engine.<br>
 * It handles the initialization and updating of the systems
 * <code>ComponentLists</code>. <br>
 * Some helper methods to make our live easier are also in this class.
 * <h3>System</h3> A System is for working with a Component of a specific type
 * to modify the Entities.
 * 
 * 
 * @author Florian Albrecht
 */
public abstract class EngineSystem {

	/**
	 * All the <code>ComponentLists</code> of this system.
	 */
	private ArrayList<CList> lists = new ArrayList<CList>();

	/**
	 * This class can only be used through subclasses.
	 */
	protected EngineSystem() {
	}

	/**
	 * This method initializes all the <code>ComponentLists</code> and calls the
	 * init() method.
	 * 
	 * @param entities
	 */
	public void initialize(EntityHandler entities) {
		for (CList l : lists) {
			entities.addComponents(l);
		}
		init();
	}

	/**
	 * All the initialization not done in the constructor and with access to the
	 * <code>Components</code>.
	 */
	protected abstract void init();

	/**
	 * 
	 * The update method gets called once every 1/60 of a second, this is where
	 * all the meat of the system lies.
	 */
	public abstract void update() throws ClassCastException;

	/**
	 * 
	 * This method is for any cleanup necessary after the Game has ended. Here
	 * all the occupied resources should be freed and deleted.
	 */
	public abstract void cleanUp();

	/**
	 * To register a List to the update process.
	 * 
	 * @param list
	 *            The List to add
	 */
	protected void addList(CList list) {
		lists.add(list);
	}

	/**
	 * This method gets called by the <code>Engine</code> class and updates all
	 * the systems lists.
	 * 
	 * @param entities
	 *            The <code>EntityHandler<code> used
	 */
	public void updateLists(EntityHandler entities) {
		for (Entity e : entities.getRemovedEntities()) {
			for (CList l : lists) {
				l.removeEntity(e.getID());
			}
		}

		for (CList l : lists) {
			entities.addComponents(l);
		}
	}
}
