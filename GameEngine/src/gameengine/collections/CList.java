/**
 * 
 */
package gameengine.collections;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;

/**
 * 
 * This is the Interface to the <code>ComponentList</code> class it is necessary
 * to have Lists of ComponentLists.
 * 
 * @author Florian Albrecht
 *
 */
public interface CList {

	/**
	 * 
	 * @param c
	 * @return
	 */
	public boolean add(Component c);

	public void removeEntity(int eID);

	public ComponentType getType();

}
