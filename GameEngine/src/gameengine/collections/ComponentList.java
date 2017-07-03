/**
 * 
 */
package gameengine.collections;

import java.util.ArrayList;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.util.exceptions.NotFoundException;

/**
 * This Class extends the ArrayList class to add the necessary functionality
 * needed for the Entity-Component System.
 * 
 * @author Florian Albrecht
 *
 */
public class ComponentList<E extends Component> extends ArrayList<E> implements CList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5397752368499171040L;

	/**
	 * Every ComponentList needs a type of the Components it contains.
	 */
	private ComponentType type;

	/**
	 * 
	 * @param type
	 *            The type of the Components in this List (<strong>Must be the
	 *            correct one</strong>)
	 */
	public ComponentList(ComponentType type) {
		this.type = type;
	}

	/**
	 * Adds a Component to the List.
	 * <h2>Warning: if the ComponentType is not the correct one this will throw
	 * a ClassCastException!</h2>
	 */
	@SuppressWarnings("unchecked")
	public boolean add(Component comp) {
		return super.add((E) comp);
	}

	/**
	 * 
	 * @param entityID
	 * @return The Component of the entity with the given id.
	 * @throws NotFoundException
	 */
	public E getComponent(int entityID) throws NotFoundException {
		for (E c : this) {
			if (c.getEntityID() == entityID)
				return c;
		}
		throw new NotFoundException("Couldn`t find component of entity " + entityID + " in " + type);
	}

	/**
	 * Checks if this list contains a Component of an entity with the given id.
	 * 
	 * @param entityID
	 *            the id of an Entity
	 * @return the index of the found component or <code>-1</code>
	 */
	public int has(int entityID) {
		int i = 0;
		for (E c : this) {
			if (c.getEntityID() == entityID)
				return i;
			i++;
		}
		return -1;
	}

	/**
	 * 
	 * @return The type of the List.
	 */
	public ComponentType getType() {
		return type;
	}

	/**
	 * This method removes the <code>Component</code> belonging to the Entity
	 * with the given id.
	 * 
	 * @param eID
	 *            The id of an <code>Entity</code>
	 */
	public void removeEntity(int eID) {
		int e = has(eID);
		if (e != -1)
			this.remove(e);
	}
}
