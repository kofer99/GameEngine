/**
 * 
 */
package gameengine.core;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * @author Florian Albrecht
 *
 */
public class GLWindow {

	public long windowID;

	public static int width = 960;
	public static int height = 540;
	private String title = "GameEngine";

	InputHandler inputHandler;
	MouseHandlerMove mouseHandlerMove;
	MouseHandlerClick mouseHandlerClick;

	public GLWindow(Keyboard keyboard, Mouse mouse) {
		inputHandler = new InputHandler(keyboard);
		mouseHandlerMove = new MouseHandlerMove(mouse);
		mouseHandlerClick = new MouseHandlerClick(mouse);
	}

	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are
									// already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden
													// after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be
													// resizable

		// Create the window
		windowID = glfwCreateWindow(width, height, title, NULL, NULL);
		if (windowID == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed,
		// repeated or released.

		// set the callbacks
		glfwSetKeyCallback(windowID, inputHandler);
		glfwSetCursorPosCallback(windowID, mouseHandlerMove);
		glfwSetMouseButtonCallback(windowID, mouseHandlerClick);

		// Set the window properties
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

		glfwWindowHint(GLFW.GLFW_RED_BITS, 8);
		glfwWindowHint(GLFW.GLFW_GREEN_BITS, 8);
		glfwWindowHint(GLFW.GLFW_BLUE_BITS, 8);
		glfwWindowHint(GLFW.GLFW_ALPHA_BITS, 8);

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		// Center the window
		glfwSetWindowPos(windowID, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		// Make the OpenGL context current
		glfwMakeContextCurrent(windowID);
		// Enable(1)/Disable(0) v-sync
		glfwSwapInterval(0);

		// Make the window visible
		glfwShowWindow(windowID);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		System.out.println("Current LWJGL version: " + Version.getVersion());
		System.out.println("Current OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
		System.out.println("Current GLSL version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(windowID);
	}

	public void poll() {
		glfwSwapBuffers(windowID); // swap the color buffers
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
	}

	public void destroy() {
		GLFW.glfwDestroyWindow(windowID);
		GLFW.glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}
