/**
 * 
 */
package gameengine.objects;

/**
 * <p>
 * This class is the super class to every <code>Component</code> in the Engine.
 * <br>
 * 
 * <h3>Components</h3> Components are the data connecting an Entity with a
 * System. The systems can operate on the Components.<br>
 * <strong> Every Component must have a <code>ComponentType<code> associated
 * with it, to identify the Components at Runtime.</strong><br>
 * A component should :
 * <ul>
 * <li>only contain data and code necessary to create that data
 * <li>only be edited by its corresponding system, but read from every system
 * </ul>
 * </p>
 * 
 * @author Florian Albrecht
 *
 */
public class Component {

	/**
	 * The type of the component, used to identify <code>Components</code> at
	 * Runtime.
	 */
	private ComponentType type;

	/**
	 * The association between <code>Entity</code> and <code>Component</code>.
	 */
	private int entityID;

	/**
	 * Only a super class.
	 */
	protected Component(ComponentType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The <code>ComponentType</code> of the <code>Component</code>
	 */
	public ComponentType getType() {
		return type;
	}

	/**
	 * Sets the Entity.<br>
	 * This method should only be called by the <code>EntityHandler</code>
	 * <strong>!</strong>
	 * 
	 * @param entityID
	 */
	public void setEntityID(int eID) {
		entityID = eID;
	}

	/**
	 * 
	 * @return The <code>Entity</code> the <code>Component</code> belongs to
	 */
	public int getEntityID() {
		return entityID;
	}

}
