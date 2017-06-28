/**
 * 
 */
package gameengine.collections;

import java.util.ArrayList;

import gameengine.objects.Component;
import gameengine.objects.Entity;

/**
 * @author Florian Albrecht
 *
 */
public class EntityHandler {

	private EntityList entities;

	private ArrayList<Entity> removedEntities = new ArrayList<Entity>(10);
	private ArrayList<Component> currentComponents = new ArrayList<Component>();

	/**
	 * 
	 */
	public EntityHandler() {
		entities = new EntityList();
	}

	/**
	 * @param e
	 */
	public void addEntity(Entity e) {
		entities.addEntity(e);
	}

	/**
	 * @param c
	 */
	public void addComponent(Component c) {
		currentComponents.add(c);
	}

	/**
	 * @param eID
	 */
	public void removeEntity(int eID) {
		removedEntities.add(entities.removeEntity(eID));
	}

	/**
	 * 
	 */
	public void flush() {
		removedEntities.clear();
		currentComponents.clear();
	}

	/**
	 * 
	 */
	public void flushAfterInit() {
		flush();
	}

	/**
	 * 
	 * @param list
	 */
	public void addComponents(CList list) {
		for (Component c : currentComponents) {
			if (c.getType() == list.getType())
				list.add(c);
		}
	}

	public ArrayList<Entity> getRemovedEntities() {
		return removedEntities;
	}

	public ArrayList<Component> getAddedComponents() {
		return currentComponents;
	}
}
