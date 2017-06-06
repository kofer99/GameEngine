package gameengine.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseHandlerClick extends GLFWMouseButtonCallback {

	private Mouse mouse;

	public MouseHandlerClick(Mouse mouse) {
		this.mouse = mouse;
	}

	@Override
	public void invoke(long window, int button, int action, int mods) {
		if (action == GLFW.GLFW_PRESS) {
			mouse.setButtonDown(button);
		}

		if (action == GLFW.GLFW_RELEASE) {
			mouse.setButtonUp(button);
		}
	}

}
