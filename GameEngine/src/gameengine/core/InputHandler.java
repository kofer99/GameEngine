package gameengine.core;

import org.lwjgl.glfw.GLFW;

import far.math.vec.Vec3f;

public class InputHandler extends org.lwjgl.glfw.GLFWKeyCallback {

	public static Vec3f lightPos = new Vec3f(0, 4, 0);

	private Keyboard keyboard;
	
	public InputHandler(Keyboard keyboard) {
		this.keyboard = keyboard;
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {

		if (action == GLFW.GLFW_PRESS) {
			if (key == GLFW.GLFW_KEY_ESCAPE) {
				GLFW.glfwSetWindowShouldClose(window, true);
			}
			keyboard.setKeyDown(key);
		}
		if (action == GLFW.GLFW_RELEASE) {
			keyboard.setKeyUp(key);
		}
	}

}
