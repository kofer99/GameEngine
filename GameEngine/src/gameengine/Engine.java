/**
 * 
 */
package gameengine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import game.TestGame;
import gameengine.collections.EntityHandler;
import gameengine.core.GLWindow;
import gameengine.core.Keyboard;
import gameengine.core.Mouse;
import gameengine.objects.Component;
import gameengine.objects.EngineSystem;
import gameengine.objects.Entity;
import gameengine.objects.Game;
import gameengine.systems.EngineActionController;
import gameengine.systems.Physics;
import gameengine.util.EngineLogger;
import gameengine.util.OpenGLErrorCatcher;

/**
 * @author Florian Albrecht
 *
 */
public class Engine {
	private GLWindow window;

	private EntityHandler entities;
	private ArrayList<EngineSystem> systems = new ArrayList<EngineSystem>();

	private EngineSystem physics;
	private EngineSystem aController;

	private Game game;

	private static final EngineLogger logger = new EngineLogger(EngineLogger.LOGALL);

	public Engine() {
		window = new GLWindow(new Keyboard(), new Mouse());
		window.init();
	}

	public void start() {
		init();
		run();
		cleanup();
	}

	private void init() {
		entities = new EntityHandler();

		// Game init
		game = new TestGame();
		game.init();

		// System init
		physics = new Physics();
		physics.initialize(entities);
		systems.add(physics);

		aController = new EngineActionController();
		aController.initialize(entities);
		systems.add(aController);

		entities.flushAfterInit();
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
	}

	/**
	 * This method calls the master renderer for rendering.
	 */
	private void render() {
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glColor3f(0.75f, 0.75f, 0.75f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(0.0f, 0.5f, -0.5f);
		GL11.glEnd();

		// swap the window and pool the events
		window.poll();
	}

	/**
	 * This method updates all the systems of the engine in order and updates
	 * the lists of the systems
	 * 
	 * @param delta
	 */
	int i = 0;

	private void update(double delta) {
		i++;
		aController.update();
		physics.update();

		for (EngineSystem sys : systems) {
			sys.updateLists(entities);
		}

		if (i % 100 == 0) {
			i = i - 1;

			i = i + 1;
		}

		entities.flush();
	}

	/**
	 * Cleans up all the loose ends
	 */
	private void cleanup() {
		window.destroy();
	}

	/**
	 * Adds an Entity.
	 * 
	 * @param e
	 *            The new Entity
	 */
	public void addEntity(Entity e) {
		entities.addEntity(e);
	}

	/**
	 * Adds a Component, witch can be retrieved through the addedComponent list
	 * of the EntityHandler.
	 * 
	 * @param c
	 *            The new Component
	 */
	public void addComponent(Component c) {
		entities.addComponent(c);
	}

	/**
	 * Removes an Entity, the Entity can be retrieved through the
	 * removedEntities list of the EnityHandler.
	 * 
	 * @param eID
	 *            The id of the Entity
	 * 
	 */
	public void removeEntity(int eID) {
		entities.removeEntity(eID);
	}

}
