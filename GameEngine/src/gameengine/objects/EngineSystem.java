/**
 * 
 */
package gameengine.objects;

import java.util.ArrayList;

import gameengine.collections.CList;
import gameengine.collections.EntityHandler;

/**
 * @author Florian Albrecht
 *
 */
public abstract class EngineSystem {

	private ArrayList<CList> lists = new ArrayList<CList>();

	protected EngineSystem() {
	}

	public void initialize(EntityHandler entities) {
		for (CList l : lists) {
			entities.addComponents(l);
		}
		init();
	}

	public abstract void init();

	public abstract void update();

	protected void addList(CList list) {
		lists.add(list);
	}

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
