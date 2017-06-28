/**
 * 
 */
package gameengine.collections;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;

/**
 * @author Florian Albrecht
 *
 */
public interface CList {

	public boolean add(Component c);

	public void removeEntity(int eID);

	public ComponentType getType();

}
