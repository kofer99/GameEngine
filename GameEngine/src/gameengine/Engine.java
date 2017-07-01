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
import gameengine.systems.InputController;
import gameengine.systems.Physics;
import gameengine.util.EngineLogger;
import gameengine.util.OpenGLErrorCatcher;

/**
 * @author Florian Albrecht
 *
 */
public class Engine {
	/**
	 * The window object.
	 */
	private GLWindow window;

	/**
	 * The keyboard object to access the user input from everywhere in the
	 * engine.
	 */
	public static Keyboard keyboard;

	/**
	 * The mouse object to access the user input from everywhere in the engine.
	 */
	public static Mouse mouse;

	/**
	 * This object handles the Entity-Component connection.
	 */
	private EntityHandler entities;

	/**
	 * This list is used to update all the systems <code>ComponentLists</code>.
	 */
	private ArrayList<EngineSystem> systems = new ArrayList<EngineSystem>();

	/**
	 * All the systems, except the MasterRenderer
	 */
	private EngineSystem physics;
	private EngineSystem aController;
	private EngineSystem inputController;

	/**
	 * The Master renderer is handled specialty, because of its
	 */
	// private MasterRenderer renderer;

	/**
	 * The Game object
	 */
	private Game game;

	/**
	 * This is only to create a Logger instance, it is never used directly but
	 * only through static members
	 */
	private static final EngineLogger logger = new EngineLogger(EngineLogger.LOGALL);

	public Engine() {
		keyboard = new Keyboard();
		mouse = new Mouse();

		window = new GLWindow(keyboard, mouse);
		window.init();
	}

	/**
	 * Controls the program flow (init, run and cleanup)
	 */
	public void start() {
		init();
		run();
		cleanup();
	}

	/**
	 * This method initializes the <code>EntityHandler</code>, the
	 * <code>Game</code> and every <code>System</code> (<strong>Order
	 * matters</strong>).
	 * <p>
	 * Every <code>System</code> has to be created, initialized and added to the
	 * systems List!
	 * </p>
	 */
	private void init() {
		entities = new EntityHandler();

		// Game init
		try {
			game = new TestGame();
			game.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System init
		physics = new Physics();
		physics.initialize(entities);
		systems.add(physics);

		aController = new EngineActionController();
		aController.initialize(entities);
		systems.add(aController);

		inputController = new InputController();
		inputController.initialize(entities);
		systems.add(inputController);

		// The EntityHandler needs to be flushed after every tick and after the
		// init
		entities.flushAfterInit();
	}

	/**
	 * This method runs the main GameLoop.
	 * <p>
	 * It calls the render and after that the update method and implements the
	 * FPS/UPS counter and logs it at the info log level. After every loop it
	 * checks for OpenGL errors.
	 * </p>
	 */
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

			if (OpenGLErrorCatcher.checkOpenGLError("Main loop") != true) {
				break;
			}
		}
	}

	/**
	 * This method calls the master renderer for rendering and swaps the
	 * buffers.
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
	 * This method updates all the systems of the engine in the correct order
	 * and updates the lists of the systems.<br>
	 * Catches if something is wrong with the ComponentLists.
	 * 
	 * @param delta
	 */
	private void update(double delta) {
		try {
			for (EngineSystem sys : systems) {
				sys.update();
			}
		} catch (ClassCastException e) {
			System.err.println("A ComponentList is messed up! (take a look at the StackTrace)");
			e.printStackTrace();
		}

		for (EngineSystem sys : systems) {
			sys.updateLists(entities);
		}

		entities.flush();
	}

	/**
	 * Cleans up all the loose ends.
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
