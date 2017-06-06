package gameengine.core;

import org.lwjgl.glfw.GLFW;

import far.math.vec.Vec2f;

public class Mouse {

	private boolean leftMouseButtonDown;
	private boolean leftMouseButtonUp;

	private boolean rightMouseButtonDown;
	private boolean rightMouseButtonUp;

	private boolean middleMouseButtonDown;
	private boolean middleMouseButtonUp;

	private Vec2f mouse;

	private Vec2f dMouse;

	private boolean firstMotion = true;

	public Mouse() {
		mouse = new Vec2f(0, 0);
		dMouse = new Vec2f(0, 0);

		clear();
	}

	public void setButtonDown(int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
			leftMouseButtonDown = true;
		if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
			rightMouseButtonDown = true;
		if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
			middleMouseButtonDown = true;

	}

	public void setButtonUp(int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
			leftMouseButtonUp = true;
		if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
			rightMouseButtonUp = true;
		if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
			middleMouseButtonUp = true;
	}

	public void move(int xpos, int ypos) {
		if (firstMotion == true) {
			dMouse.x = 0;
			dMouse.y = 0;
		} else {
			dMouse.x = xpos - mouse.x;
			dMouse.y = ypos - mouse.y;
		}
		mouse.x = xpos;
		mouse.y = ypos;
	}

	public Vec2f getMouse() {
		return mouse;
	}

	public Vec2f getdMouse() {
		return dMouse;
	}

	public boolean isLeftMouseButtonDown() {
		return leftMouseButtonDown;
	}

	public boolean isLeftMouseButtonUp() {
		return leftMouseButtonUp;
	}

	public boolean isRightMouseButtonDown() {
		return rightMouseButtonDown;
	}

	public boolean isRightMouseButtonUp() {
		return rightMouseButtonUp;
	}

	public boolean isMiddleMouseButtonDown() {
		return middleMouseButtonDown;
	}

	public boolean isMiddleMouseButtonUp() {
		return middleMouseButtonUp;
	}

	public boolean isFirstMotion() {
		return firstMotion;
	}

	public void tick() {
		clear();
	}

	public void tickFirst() {
		firstMotion = !firstMotion;
	}

	private void clear() {
		leftMouseButtonDown = false;
		leftMouseButtonUp = false;

		rightMouseButtonDown = false;
		rightMouseButtonUp = false;

		middleMouseButtonDown = false;
		middleMouseButtonUp = false;
		
		dMouse.x = 0;
		dMouse.y = 0;
	}

	public boolean isDown(int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
			return leftMouseButtonDown;
		if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
			return rightMouseButtonDown;
		if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
			return middleMouseButtonDown;

		System.out.println("unown mouse button: " + button);
		return false;
	}

	public boolean isUp(int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
			return leftMouseButtonUp;
		if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
			return rightMouseButtonUp;
		if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
			return middleMouseButtonUp;

		System.out.println("unown mouse button: " + button);
		return false;
	}

}
