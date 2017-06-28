/**
 * 
 */
package gameengine.objects;

/**
 * @author Florian Albrecht
 *
 */
public class Component {

	private ComponentType type;

	private int entityID;

	/**
	 * 
	 */
	protected Component(ComponentType type) {
		this.type = type;
	}

	public ComponentType getType() {
		return type;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public int getEntityID() {
		return entityID;
	}

}
