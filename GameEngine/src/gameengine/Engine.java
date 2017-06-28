/**
 * 
 */
package gameengine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.opengl.GL11;

import gameengine.core.GLWindow;
import gameengine.core.Keyboard;
import gameengine.core.Mouse;
import gameengine.util.OpenGLErrorCatcher;

/**
 * @author Florian Albrecht
 *
 */
public class Engine {
	GLWindow window;

	public Engine() {
		window = new GLWindow(new Keyboard(), new Mouse());
		window.init();
		run();
	}

	private void run() {
		int updates = 0;
		int frames = 0;

		long lastTimeF = System.currentTimeMillis();
		long lastTimeD = System.nanoTime();
		long thisTime = 0;

		final double ticks = 60D;
		final double ns = 1000000000 / ticks;
		double delta = 0;

		while (!window.shouldClose()) {
			// Clear all
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glClearColor(0.25f, 0.25f, 0.25f, 0.0f);

			render();

			thisTime = System.nanoTime();
			delta += (thisTime - lastTimeD) / ns;
			lastTimeD = thisTime;
			if (delta >= 1) {
				update(delta);
				updates++;
				delta -= 1;
			}

			frames++;
			thisTime = System.currentTimeMillis();
			if (thisTime - lastTimeF >= 1000) {
				System.out.println("FPS: " + frames + ", f/ms: " + (1000.0 / frames) + ", UPS: " + updates);
				lastTimeF = thisTime;
				updates = 0;
				frames = 0;
			}

			if (OpenGLErrorCatcher.checkOpenGLError("render loop") != true) {
				break;
			}
		}
		cleanUp();
	}

	private void render() {
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glColor3f(0.75f, 0.75f, 0.75f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(0.0f, 0.5f, -0.5f);
		GL11.glEnd();
		window.poll();
	}

	private void update(double delta) {
	}

	private void cleanUp() {
		window.destroy();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Engine engine = new Engine();
	}
}
