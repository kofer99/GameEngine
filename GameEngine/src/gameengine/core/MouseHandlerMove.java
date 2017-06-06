package gameengine.core;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseHandlerMove extends GLFWCursorPosCallback {

	public static double xPosition = 0;
	public static double yPosition = 0;

	private Mouse mouse;

	public MouseHandlerMove(Mouse mouse) {
		this.mouse = mouse;
	}

	@Override
	public void invoke(long window, double xpos, double ypos) {
		mouse.move((int) xpos, (int) ypos);
	}

}