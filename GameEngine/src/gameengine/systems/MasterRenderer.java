/**
 * 
 */
package gameengine.systems;

import org.lwjgl.opengl.GL11;

import gameengine.objects.EngineSystem;

/**
 * @author Florian Albrecht
 *
 */
public class MasterRenderer extends EngineSystem {

	public MasterRenderer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	// AKA render()
	@Override
	public void update() throws ClassCastException {
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glColor3f(0.75f, 0.75f, 0.75f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(0.0f, 0.5f, -0.5f);
		GL11.glEnd();

	}

}
