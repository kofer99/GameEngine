/**
 * 
 */
package gameengine.systems;

import org.lwjgl.opengl.GL11;

import gameengine.collections.ComponentList;
import gameengine.collections.EntityHandler;
import gameengine.components.Renderable;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
 *
 */
public class MasterRenderer extends EngineSystem {
	private ComponentList<Renderable> renderable;
	public MasterRenderer() {
		// TODO Auto-generated constructor stub
		renderable = new ComponentList<Renderable>(ComponentType.RENDERABLE);
		super.addList(renderable);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
	
	}
	/* (non-Javadoc)
	 * @see gameengine.objects.EngineSystem#initialize(gameengine.collections.EntityHandler)
	 */
	@Override
	public void initialize(EntityHandler entities) {
		// TODO Auto-generated method stub
		entities.addComponents(renderable);
		super.initialize(entities);
	}
	// AKA render()
	@Override
	public void update() throws ClassCastException {
	/*	GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glColor3f(0.75f, 0.75f, 0.75f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(0.0f, 0.5f, -0.5f);
		GL11.glEnd();*/
		
		for(Renderable r: renderable){
			
			r.render();
			}

	}

}
